package com.jihan.composeutils.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.AnimationScope
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.core.noRippleEffect
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CxWheelTimePicker(
    modifier: Modifier = Modifier,
    title: String = "TIME PICKER",
    doneLabel: String = "Done",
    titleStyle: TextStyle = LocalTextStyle.current,
    doneLabelStyle: TextStyle = LocalTextStyle.current,
    startTime: LocalTime = LocalTime.now(),
    minTime: LocalTime = LocalTime.MIN,
    maxTime: LocalTime = LocalTime.MAX,
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    size: DpSize = DpSize(128.dp, 128.dp),
    rowCount: Int = 3,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onDismiss: () -> Unit = {},
    onDoneClick: (snappedDate: LocalTime) -> Unit = {},
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    var selectedDate by remember { mutableStateOf(LocalTime.now()) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(modifier = modifier) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title, style = titleStyle, modifier = Modifier.fillMaxWidth()
                )
                Text(text = doneLabel,
                    style = doneLabelStyle,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .align(Alignment.CenterEnd)
                        .noRippleEffect {
                            onDoneClick(selectedDate)
                        })
            }
            HorizontalDivider(
                modifier = Modifier.padding(top = 12.dp),
                thickness = (0.5).dp,
                color = Color.LightGray
            )
            CxCustomTimePicker(
                textColor = textColor,
                timeFormat = timeFormat,
                selectorProperties = selectorProperties,
                rowCount = rowCount,
                size = size,
                modifier = Modifier.padding(top = 14.dp, bottom = 40.dp),
                startTime = startTime,
                minTime = minTime,
                maxTime = maxTime,
                textStyle = textStyle,
            ) { snappedTime ->
                selectedDate = snappedTime
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CxCustomTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    minTime: LocalTime = LocalTime.MIN,
    maxTime: LocalTime = LocalTime.MAX,
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    size: DpSize = DpSize(128.dp, 128.dp),
    rowCount: Int = 3,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedTime: (snappedTime: LocalTime) -> Unit = {},
) {
    DefaultWheelTimePicker(modifier = modifier,
        startTime = startTime,
        minTime = minTime,
        maxTime = maxTime,
        timeFormat = timeFormat,
        size = size,
        rowCount = rowCount,
        textStyle = textStyle,
        textColor = textColor,
        selectorProperties = selectorProperties,
        onSnappedTime = { snappedTime, _ ->
            onSnappedTime(snappedTime.snappedLocalTime)
            snappedTime.snappedIndex
        })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun DefaultWheelTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    minTime: LocalTime = LocalTime.MIN,
    maxTime: LocalTime = LocalTime.MAX,
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    size: DpSize = DpSize(128.dp, 128.dp),
    rowCount: Int = 3,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(borderColor = Color.Transparent),
    onSnappedTime: (snappedTime: SnappedTime, timeFormat: TimeFormat) -> Int? = { _, _ -> null },
) {

    var snappedTime by remember { mutableStateOf(startTime.truncatedTo(ChronoUnit.MINUTES)) }

    val hours = (0..23).map {
        Hour(
            text = it.toString().padStart(2, '0'), value = it, index = it
        )
    }
    val amPmHours = (1..12).map {
        AmPmHour(
            text = it.toString(), value = it, index = it - 1
        )
    }

    val minutes = (0..59).map {
        Minute(
            text = it.toString().padStart(2, '0'), value = it, index = it
        )
    }

    val amPms = listOf(
        AmPm(
            text = "AM", value = AmPmValue.AM, index = 0
        ), AmPm(
            text = "PM", value = AmPmValue.PM, index = 1
        )
    )

    var snappedAmPm by remember {
        mutableStateOf(amPms.find { it.value == amPmValueFromTime(startTime) } ?: amPms[0])
    }

    Box(
        modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        if (selectorProperties.enabled().value) {
            HorizontalDivider(
                modifier = Modifier.padding(bottom = (size.height / rowCount)),
                thickness = (0.5).dp,
                color = selectorProperties.color().value
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = (size.height / rowCount)),
                thickness = (0.5).dp,
                color = selectorProperties.color().value
            )
        }
        Row(
            Modifier.fillMaxWidth(0.8f),
        ) {
            CxWheelTextPicker(modifier = Modifier.weight(1f),
                startIndex = if (timeFormat == TimeFormat.HOUR_24) {
                    hours.find { it.value == startTime.hour }?.index ?: 0
                } else amPmHours.find { it.value == localTimeToAmPmHour(startTime) }?.index ?: 0,
                size = DpSize(
                    width = size.width / if (timeFormat == TimeFormat.HOUR_24) 2 else 3,
                    height = size.height
                ),
                texts = if (timeFormat == TimeFormat.HOUR_24) hours.map { it.text } else amPmHours.map { it.text },
                rowCount = rowCount,
                style = textStyle,
                color = textColor
            ) { snappedIndex ->

                val newHour = if (timeFormat == TimeFormat.HOUR_24) {
                    hours.find { it.index == snappedIndex }?.value
                } else {
                    amPmHourToHour24(
                        amPmHours.find { it.index == snappedIndex }?.value ?: 0,
                        snappedTime.minute,
                        snappedAmPm.value
                    )
                }

                newHour?.let {

                    val newTime = snappedTime.withHour(newHour)

                    if (!newTime.isBefore(minTime) && !newTime.isAfter(maxTime)) {
                        snappedTime = newTime
                    }

                    val newIndex = if (timeFormat == TimeFormat.HOUR_24) {
                        hours.find { it.value == snappedTime.hour }?.index
                    } else {
                        amPmHours.find { it.value == localTimeToAmPmHour(snappedTime) }?.index
                    }

                    newIndex?.let {
                        onSnappedTime(
                            SnappedTime.Hour(
                                localTime = snappedTime, index = newIndex
                            ), timeFormat
                        )?.let { return@CxWheelTextPicker it }
                    }
                }

                return@CxWheelTextPicker if (timeFormat == TimeFormat.HOUR_24) {
                    hours.find { it.value == snappedTime.hour }?.index
                } else {
                    amPmHours.find { it.value == localTimeToAmPmHour(snappedTime) }?.index
                }
            }
            Box(
                modifier = Modifier.height(size.height), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = ":", style = textStyle, color = textColor
                )
            }
            CxWheelTextPicker(
                modifier = Modifier.weight(1f),
                startIndex = minutes.find { it.value == startTime.minute }?.index ?: 0,
                size = DpSize(
                    width = size.width / if (timeFormat == TimeFormat.HOUR_24) 2 else 3,
                    height = size.height
                ),
                texts = minutes.map { it.text },
                rowCount = rowCount,
                style = textStyle,
                color = textColor
            ) { snappedIndex ->

                val newMinute = minutes.find { it.index == snappedIndex }?.value

                val newHour = if (timeFormat == TimeFormat.HOUR_24) {
                    hours.find { it.value == snappedTime.hour }?.value
                } else {
                    amPmHourToHour24(
                        amPmHours.find { it.value == localTimeToAmPmHour(snappedTime) }?.value ?: 0,
                        snappedTime.minute,
                        snappedAmPm.value
                    )
                }

                newMinute?.let {
                    newHour?.let {
                        val newTime = snappedTime.withMinute(newMinute).withHour(newHour)

                        if (!newTime.isBefore(minTime) && !newTime.isAfter(maxTime)) {
                            snappedTime = newTime
                        }

                        val newIndex = minutes.find { it.value == snappedTime.minute }?.index

                        newIndex?.let {
                            onSnappedTime(
                                SnappedTime.Minute(
                                    localTime = snappedTime, index = newIndex
                                ), timeFormat
                            )?.let { return@CxWheelTextPicker it }
                        }
                    }
                }

                return@CxWheelTextPicker minutes.find { it.value == snappedTime.minute }?.index
            }
            if (timeFormat == TimeFormat.AM_PM) {
                CxWheelTextPicker(
                    modifier = Modifier.weight(1f),
                    startIndex = amPms.find { it.value == amPmValueFromTime(startTime) }?.index
                        ?: 0,
                    size = DpSize(
                        width = size.width / 3, height = size.height
                    ),
                    texts = amPms.map { it.text },
                    rowCount = rowCount,
                    style = textStyle,
                    color = textColor
                ) { snappedIndex ->

                    val newAmPm = amPms.find {
                        if (snappedIndex == 2) {
                            it.index == 1
                        } else {
                            it.index == snappedIndex
                        }
                    }

                    newAmPm?.let {
                        snappedAmPm = newAmPm
                    }

                    val newMinute = minutes.find { it.value == snappedTime.minute }?.value

                    val newHour = amPmHourToHour24(
                        amPmHours.find { it.value == localTimeToAmPmHour(snappedTime) }?.value ?: 0,
                        snappedTime.minute,
                        snappedAmPm.value
                    )

                    newMinute?.let {
                        val newTime = snappedTime.withMinute(newMinute).withHour(newHour)

                        if (!newTime.isBefore(minTime) && !newTime.isAfter(maxTime)) {
                            snappedTime = newTime
                        }

                        val newIndex = minutes.find { it.value == snappedTime.hour }?.index

                        newIndex?.let {
                            onSnappedTime(
                                SnappedTime.Hour(
                                    localTime = snappedTime, index = newIndex
                                ), timeFormat
                            )
                        }
                    }

                    return@CxWheelTextPicker snappedIndex
                }
            }
            Spacer(modifier = Modifier.weight(0.7f))
        }
    }
}

@Composable
fun CxWheelTextPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    size: DpSize = DpSize(128.dp, 128.dp),
    texts: List<String>,
    rowCount: Int,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = LocalContentColor.current,
    contentAlignment: Alignment = Alignment.Center,
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
) {
    WheelPicker(
        modifier = modifier,
        startIndex = startIndex,
        count = texts.size,
        rowCount = rowCount,
        size = size,
        onScrollFinished = onScrollFinished,
        texts = texts,
        style = style,
        color = color,
        contentAlignment = contentAlignment
    )
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
internal fun WheelPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    count: Int,
    rowCount: Int,
    size: DpSize = DpSize(128.dp, 128.dp),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    texts: List<String>,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = LocalContentColor.current,
    contentAlignment: Alignment = Alignment.Center,
) {
    val lazyListState = rememberLazyListState(startIndex)
    val snapperLayoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState = lazyListState)
    val isScrollInProgress = lazyListState.isScrollInProgress

    LaunchedEffect(isScrollInProgress, count) {
        if (!isScrollInProgress) {
            onScrollFinished(calculateSnappedItemIndex(snapperLayoutInfo) ?: startIndex)?.let {
                lazyListState.scrollToItem(it)
            }
        }
    }

    val topBottomFade = Brush.verticalGradient(
        0f to Color.Transparent, 0.3f to Color.Black, 0.7f to Color.Black, 1f to Color.Transparent
    )

    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .height(size.height)
                .fadingEdge(topBottomFade)
                .fillMaxWidth(),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = size.height / rowCount * ((rowCount - 1) / 2)),
            flingBehavior = rememberSnapperFlingBehavior(
                lazyListState = lazyListState
            )
        ) {
            items(count) { index ->
                Box(
                    modifier = Modifier
                        .height(size.height / rowCount)
                        .fillMaxWidth()
                        .alpha(
                            calculateAnimatedAlpha(
                                lazyListState = lazyListState,
                                snapperLayoutInfo = snapperLayoutInfo,
                                index = index,
                                rowCount = rowCount
                            )
                        ), contentAlignment = contentAlignment
                ) {
                    Text(
                        text = texts[index],
                        style = style,
                        color = if (calculateSnappedItemIndex(snapperLayoutInfo) == index) color else Color.Black,
                        maxLines = 1,
                        fontSize = if (calculateSnappedItemIndex(snapperLayoutInfo) == index) 20.sp else 18.sp
                    )
                }
            }
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
fun timeToString(currentTime: LocalTime, givenFormat: String): String {
    val formatter = DateTimeFormatter.ofPattern(givenFormat)
    return currentTime.format(formatter)
}

@RequiresOptIn(message = "Snapper is experimental. The API may be changed in the future.")
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalSnapperApi

@ExperimentalSnapperApi
object SnapperFlingBehaviorDefaults {
    val SpringAnimationSpec: AnimationSpec<Float> = spring(stiffness = 400f)

    @Deprecated("The maximumFlingDistance parameter has been deprecated.")
    val MaximumFlingDistance: (SnapperLayoutInfo) -> Float = { Float.MAX_VALUE }

    val SnapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int =
        { _, _, targetIndex -> targetIndex }
}

@ExperimentalSnapperApi
@Composable
fun rememberSnapperFlingBehavior(
    layoutInfo: SnapperLayoutInfo,
    decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
    springAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
    snapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int,
): SnapperFlingBehavior = remember(
    layoutInfo,
    decayAnimationSpec,
    springAnimationSpec,
    snapIndex,
) {
    SnapperFlingBehavior(
        layoutInfo = layoutInfo,
        decayAnimationSpec = decayAnimationSpec,
        springAnimationSpec = springAnimationSpec,
        snapIndex = snapIndex,
    )
}

@Suppress("DEPRECATION")
@ExperimentalSnapperApi
@Deprecated("The maximumFlingDistance parameter has been replaced with snapIndex")
@Composable
fun rememberSnapperFlingBehavior(
    layoutInfo: SnapperLayoutInfo,
    decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
    springAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
    maximumFlingDistance: (SnapperLayoutInfo) -> Float = SnapperFlingBehaviorDefaults.MaximumFlingDistance,
): SnapperFlingBehavior = remember(
    layoutInfo,
    decayAnimationSpec,
    springAnimationSpec,
    maximumFlingDistance,
) {
    SnapperFlingBehavior(
        layoutInfo = layoutInfo,
        decayAnimationSpec = decayAnimationSpec,
        springAnimationSpec = springAnimationSpec,
        maximumFlingDistance = maximumFlingDistance,
    )
}

@ExperimentalSnapperApi
abstract class SnapperLayoutInfo {

    abstract val startScrollOffset: Int

    abstract val endScrollOffset: Int

    abstract val visibleItems: Sequence<SnapperLayoutItemInfo>

    abstract val currentItem: SnapperLayoutItemInfo?

    abstract val totalItemsCount: Int

    abstract fun determineTargetIndex(
        velocity: Float,
        decayAnimationSpec: DecayAnimationSpec<Float>,
        maximumFlingDistance: Float,
    ): Int

    abstract fun distanceToIndexSnap(index: Int): Int

    abstract fun canScrollTowardsStart(): Boolean

    abstract fun canScrollTowardsEnd(): Boolean
}

abstract class SnapperLayoutItemInfo {
    abstract val index: Int
    abstract val offset: Int
    abstract val size: Int

    override fun toString(): String {
        return "SnapperLayoutItemInfo(index=$index, offset=$offset, size=$size)"
    }
}

@ExperimentalSnapperApi
@Suppress("unused")
object SnapOffsets {

    val Start: (SnapperLayoutInfo, SnapperLayoutItemInfo) -> Int =
        { layout, _ -> layout.startScrollOffset }

    val Center: (SnapperLayoutInfo, SnapperLayoutItemInfo) -> Int = { layout, item ->
        layout.startScrollOffset + (layout.endScrollOffset - layout.startScrollOffset - item.size) / 2
    }

    val End: (SnapperLayoutInfo, SnapperLayoutItemInfo) -> Int = { layout, item ->
        layout.endScrollOffset - item.size
    }
}

@ExperimentalSnapperApi
class SnapperFlingBehavior private constructor(
    private val layoutInfo: SnapperLayoutInfo,
    private val decayAnimationSpec: DecayAnimationSpec<Float>,
    private val springAnimationSpec: AnimationSpec<Float>,
    private val snapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int,
    private val maximumFlingDistance: (SnapperLayoutInfo) -> Float,
) : FlingBehavior {

    constructor(
        layoutInfo: SnapperLayoutInfo,
        decayAnimationSpec: DecayAnimationSpec<Float>,
        springAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
        snapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int = SnapperFlingBehaviorDefaults.SnapIndex,
    ) : this(
        layoutInfo = layoutInfo,
        decayAnimationSpec = decayAnimationSpec,
        springAnimationSpec = springAnimationSpec,
        snapIndex = snapIndex,
        maximumFlingDistance = @Suppress("DEPRECATION") (SnapperFlingBehaviorDefaults.MaximumFlingDistance),
    )

    @Deprecated("The maximumFlingDistance parameter has been replaced with snapIndex")
    @Suppress("DEPRECATION")
    constructor(
        layoutInfo: SnapperLayoutInfo,
        decayAnimationSpec: DecayAnimationSpec<Float>,
        springAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
        maximumFlingDistance: (SnapperLayoutInfo) -> Float = SnapperFlingBehaviorDefaults.MaximumFlingDistance,
    ) : this(
        layoutInfo = layoutInfo,
        decayAnimationSpec = decayAnimationSpec,
        springAnimationSpec = springAnimationSpec,
        maximumFlingDistance = maximumFlingDistance,
        snapIndex = SnapperFlingBehaviorDefaults.SnapIndex,
    )

    private var animationTarget: Int? by mutableStateOf(null)

    override suspend fun ScrollScope.performFling(
        initialVelocity: Float
    ): Float {
        if (!layoutInfo.canScrollTowardsStart() || !layoutInfo.canScrollTowardsEnd()) {
            return initialVelocity
        }

        SnapperLog.d { "performFling. initialVelocity: $initialVelocity" }

        val maxFlingDistance = maximumFlingDistance(layoutInfo)
        require(maxFlingDistance > 0) {
            "Distance returned by maximumFlingDistance should be greater than 0"
        }

        val initialItem = layoutInfo.currentItem ?: return initialVelocity

        val targetIndex = layoutInfo.determineTargetIndex(
            velocity = initialVelocity,
            decayAnimationSpec = decayAnimationSpec,
            maximumFlingDistance = maxFlingDistance,
        ).let { target ->
            snapIndex(
                layoutInfo,
                if (initialVelocity < 0) initialItem.index + 1 else initialItem.index,
                target,
            )
        }.also {
            require(it in 0 until layoutInfo.totalItemsCount)
        }

        return flingToIndex(index = targetIndex, initialVelocity = initialVelocity)
    }

    private suspend fun ScrollScope.flingToIndex(
        index: Int,
        initialVelocity: Float,
    ): Float {
        val initialItem = layoutInfo.currentItem ?: return initialVelocity

        if (initialItem.index == index && layoutInfo.distanceToIndexSnap(initialItem.index) == 0) {
            SnapperLog.d {
                "flingToIndex. Skipping fling, already at target. vel:$initialVelocity, initial item: $initialItem, target: $index"
            }
            return consumeVelocityIfNotAtScrollEdge(initialVelocity)
        }

        var velocityLeft = initialVelocity

        if (decayAnimationSpec.canDecayBeyondCurrentItem(initialVelocity, initialItem)) {
            velocityLeft = performDecayFling(
                initialItem = initialItem,
                targetIndex = index,
                initialVelocity = velocityLeft,
            )
        }

        val currentItem = layoutInfo.currentItem ?: return initialVelocity
        if (currentItem.index != index || layoutInfo.distanceToIndexSnap(index) != 0) {
            velocityLeft = performSpringFling(
                initialItem = currentItem,
                targetIndex = index,
                initialVelocity = velocityLeft,
            )
        }

        return consumeVelocityIfNotAtScrollEdge(velocityLeft)
    }

    private suspend fun ScrollScope.performDecayFling(
        initialItem: SnapperLayoutItemInfo,
        targetIndex: Int,
        initialVelocity: Float,
        flingThenSpring: Boolean = true,
    ): Float {
        if (initialItem.index == targetIndex && layoutInfo.distanceToIndexSnap(initialItem.index) == 0) {
            SnapperLog.d {
                "performDecayFling. Skipping decay, already at target. vel:$initialVelocity, current item: $initialItem, target: $targetIndex"
            }
            return consumeVelocityIfNotAtScrollEdge(initialVelocity)
        }

        SnapperLog.d {
            "Performing decay fling. vel:$initialVelocity, current item: $initialItem, target: $targetIndex"
        }

        var velocityLeft = initialVelocity
        var lastValue = 0f

        val canSpringThenFling = flingThenSpring && abs(targetIndex - initialItem.index) >= 2

        try {
            animationTarget = targetIndex

            AnimationState(
                initialValue = 0f,
                initialVelocity = initialVelocity,
            ).animateDecay(decayAnimationSpec) {
                val delta = value - lastValue
                val consumed = scrollBy(delta)
                lastValue = value
                velocityLeft = velocity

                if (abs(delta - consumed) > 0.5f) {
                    cancelAnimation()
                }

                val currentItem = layoutInfo.currentItem
                if (currentItem == null) {
                    cancelAnimation()
                    return@animateDecay
                }

                if (isRunning && canSpringThenFling) {
                    if (velocity > 0 && currentItem.index == targetIndex - 1) {
                        cancelAnimation()
                    } else if (velocity < 0 && currentItem.index == targetIndex) {
                        cancelAnimation()
                    }
                }

                if (isRunning && performSnapBackIfNeeded(
                        currentItem, targetIndex, ::scrollBy
                    )
                ) {
                    cancelAnimation()
                }
            }
        } finally {
            animationTarget = null
        }

        SnapperLog.d {
            "Decay fling finished. Distance: $lastValue. Final vel: $velocityLeft"
        }

        return velocityLeft
    }

    private suspend fun ScrollScope.performSpringFling(
        initialItem: SnapperLayoutItemInfo,
        targetIndex: Int,
        initialVelocity: Float = 0f,
    ): Float {
        SnapperLog.d {
            "performSpringFling. vel:$initialVelocity, initial item: $initialItem, target: $targetIndex"
        }

        var velocityLeft = when {
            targetIndex > initialItem.index && initialVelocity > 0 -> initialVelocity
            targetIndex <= initialItem.index && initialVelocity < 0 -> initialVelocity
            else -> 0f
        }
        var lastValue = 0f

        try {
            animationTarget = targetIndex

            AnimationState(
                initialValue = lastValue,
                initialVelocity = velocityLeft,
            ).animateTo(
                targetValue = layoutInfo.distanceToIndexSnap(targetIndex).toFloat(),
                animationSpec = springAnimationSpec,
            ) {
                val delta = value - lastValue
                val consumed = scrollBy(delta)
                lastValue = value
                velocityLeft = velocity

                val currentItem = layoutInfo.currentItem
                if (currentItem == null) {
                    cancelAnimation()
                    return@animateTo
                }

                if (performSnapBackIfNeeded(currentItem, targetIndex, ::scrollBy)) {
                    cancelAnimation()
                } else if (abs(delta - consumed) > 0.5f) {
                    cancelAnimation()
                }
            }
        } finally {
            animationTarget = null
        }

        SnapperLog.d {
            "Spring fling finished. Distance: $lastValue. Final vel: $velocityLeft"
        }

        return velocityLeft
    }

    private fun AnimationScope<Float, AnimationVector1D>.performSnapBackIfNeeded(
        currentItem: SnapperLayoutItemInfo,
        targetIndex: Int,
        scrollBy: (pixels: Float) -> Float,
    ): Boolean {
        SnapperLog.d {
            "scroll tick. vel:$velocity, current item: $currentItem"
        }

        val snapBackAmount = calculateSnapBack(velocity, currentItem, targetIndex)

        if (snapBackAmount != 0) {
            SnapperLog.d {
                "Scrolled past item. vel:$velocity, current item: $currentItem} target:$targetIndex"
            }
            scrollBy(snapBackAmount.toFloat())
            return true
        }

        return false
    }

    private fun DecayAnimationSpec<Float>.canDecayBeyondCurrentItem(
        velocity: Float,
        currentItem: SnapperLayoutItemInfo,
    ): Boolean {
        if (velocity.absoluteValue < 0.5f) return false

        val flingDistance = calculateTargetValue(0f, velocity)

        SnapperLog.d {
            "canDecayBeyondCurrentItem. initialVelocity: $velocity, flingDistance: $flingDistance, current item: $currentItem"
        }

        return if (velocity < 0) {
            flingDistance <= layoutInfo.distanceToIndexSnap(currentItem.index)
        } else {
            flingDistance >= layoutInfo.distanceToIndexSnap(currentItem.index + 1)
        }
    }

    private fun calculateSnapBack(
        initialVelocity: Float,
        currentItem: SnapperLayoutItemInfo,
        targetIndex: Int,
    ): Int = when {
        initialVelocity > 0 && currentItem.index >= targetIndex -> {
            layoutInfo.distanceToIndexSnap(currentItem.index)
        }

        initialVelocity < 0 && currentItem.index <= targetIndex - 1 -> {
            layoutInfo.distanceToIndexSnap(currentItem.index + 1)
        }

        else -> 0
    }

    private fun consumeVelocityIfNotAtScrollEdge(velocity: Float): Float {
        if (velocity < 0 && !layoutInfo.canScrollTowardsStart()) {
            return velocity
        } else if (velocity > 0 && !layoutInfo.canScrollTowardsEnd()) {
            return velocity
        }
        return 0f
    }
}

@ExperimentalSnapperApi
@Composable
fun rememberSnapperFlingBehavior(
    lazyListState: LazyListState,
    snapOffsetForItem: (layoutInfo: SnapperLayoutInfo, item: SnapperLayoutItemInfo) -> Int = SnapOffsets.Center,
    decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
    springAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
    snapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int = SnapperFlingBehaviorDefaults.SnapIndex,
): SnapperFlingBehavior = rememberSnapperFlingBehavior(
    layoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState, snapOffsetForItem),
    decayAnimationSpec = decayAnimationSpec,
    springAnimationSpec = springAnimationSpec,
    snapIndex = snapIndex,
)

@Deprecated(
    "endContentPadding is no longer necessary to be passed in",
    ReplaceWith("rememberSnapperFlingBehavior(lazyListState, snapOffsetForItem, decayAnimationSpec, springAnimationSpec, snapIndex)")
)
@ExperimentalSnapperApi
@Composable
fun rememberSnapperFlingBehavior(
    lazyListState: LazyListState,
    snapOffsetForItem: (layoutInfo: SnapperLayoutInfo, item: SnapperLayoutItemInfo) -> Int = SnapOffsets.Center,
    @Suppress("UNUSED_PARAMETER") endContentPadding: Dp = 0.dp,
    decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
    springAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
    snapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int = SnapperFlingBehaviorDefaults.SnapIndex,
): SnapperFlingBehavior = rememberSnapperFlingBehavior(
    layoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState, snapOffsetForItem),
    decayAnimationSpec = decayAnimationSpec,
    springAnimationSpec = springAnimationSpec,
    snapIndex = snapIndex,
)

@Composable
@Deprecated("The maximumFlingDistance parameter has been replaced with snapIndex")
@Suppress("DEPRECATION")
@ExperimentalSnapperApi
fun rememberSnapperFlingBehavior(
    lazyListState: LazyListState,
    snapOffsetForItem: (layoutInfo: SnapperLayoutInfo, item: SnapperLayoutItemInfo) -> Int = SnapOffsets.Center,
    endContentPadding: Dp = 0.dp,
    decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
    springAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
    maximumFlingDistance: (SnapperLayoutInfo) -> Float,
): SnapperFlingBehavior = rememberSnapperFlingBehavior(
    layoutInfo = rememberLazyListSnapperLayoutInfo(
        lazyListState = lazyListState,
        snapOffsetForItem = snapOffsetForItem,
        endContentPadding = endContentPadding
    ),
    decayAnimationSpec = decayAnimationSpec,
    springAnimationSpec = springAnimationSpec,
    maximumFlingDistance = maximumFlingDistance,
)

@Deprecated(
    "endContentPadding is no longer necessary to be passed in",
    ReplaceWith("rememberLazyListSnapperLayoutInfo(lazyListState, snapOffsetForItem)")
)
@ExperimentalSnapperApi
@Composable
fun rememberLazyListSnapperLayoutInfo(
    lazyListState: LazyListState,
    snapOffsetForItem: (layoutInfo: SnapperLayoutInfo, item: SnapperLayoutItemInfo) -> Int = SnapOffsets.Center,
    @Suppress("UNUSED_PARAMETER") endContentPadding: Dp = 0.dp,
): LazyListSnapperLayoutInfo {
    return rememberLazyListSnapperLayoutInfo(lazyListState, snapOffsetForItem)
}

@ExperimentalSnapperApi
@Composable
fun rememberLazyListSnapperLayoutInfo(
    lazyListState: LazyListState,
    snapOffsetForItem: (layoutInfo: SnapperLayoutInfo, item: SnapperLayoutItemInfo) -> Int = SnapOffsets.Center,
): LazyListSnapperLayoutInfo = remember(lazyListState, snapOffsetForItem) {
    LazyListSnapperLayoutInfo(
        lazyListState = lazyListState,
        snapOffsetForItem = snapOffsetForItem,
    )
}

@ExperimentalSnapperApi
class LazyListSnapperLayoutInfo(
    private val lazyListState: LazyListState,
    private val snapOffsetForItem: (layoutInfo: SnapperLayoutInfo, item: SnapperLayoutItemInfo) -> Int,
) : SnapperLayoutInfo() {

    @Deprecated(
        "endContentPadding is no longer necessary to be passed in",
        ReplaceWith("LazyListSnapperLayoutInfo(lazyListState, snapOffsetForItem)")
    )
    constructor(
        lazyListState: LazyListState,
        snapOffsetForItem: (layoutInfo: SnapperLayoutInfo, item: SnapperLayoutItemInfo) -> Int,
        @Suppress("UNUSED_PARAMETER") endContentPadding: Int = 0,
    ) : this(lazyListState, snapOffsetForItem)

    override val startScrollOffset: Int = 0

    override val endScrollOffset: Int
        get() = lazyListState.layoutInfo.let { it.viewportEndOffset - it.afterContentPadding }

    private val itemCount: Int get() = lazyListState.layoutInfo.totalItemsCount

    override val totalItemsCount: Int
        get() = lazyListState.layoutInfo.totalItemsCount

    override val currentItem: SnapperLayoutItemInfo? by derivedStateOf {
        visibleItems.lastOrNull { it.offset <= snapOffsetForItem(this, it) }
    }

    override val visibleItems: Sequence<SnapperLayoutItemInfo>
        get() = lazyListState.layoutInfo.visibleItemsInfo.asSequence()
            .map(::LazyListSnapperLayoutItemInfo)

    override fun distanceToIndexSnap(index: Int): Int {
        val itemInfo = visibleItems.firstOrNull { it.index == index }
        if (itemInfo != null) {
            return itemInfo.offset - snapOffsetForItem(this, itemInfo)
        }

        val currentItem = currentItem ?: return 0 // TODO: throw?
        return ((index - currentItem.index) * estimateDistancePerItem()).roundToInt() + currentItem.offset - snapOffsetForItem(
            this, currentItem
        )
    }

    override fun canScrollTowardsStart(): Boolean {
        return lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.let {
            it.index > 0 || it.offset < startScrollOffset
        } ?: false
    }

    override fun canScrollTowardsEnd(): Boolean {
        return lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let {
            it.index < itemCount - 1 || (it.offset + it.size) > endScrollOffset
        } ?: false
    }

    override fun determineTargetIndex(
        velocity: Float,
        decayAnimationSpec: DecayAnimationSpec<Float>,
        maximumFlingDistance: Float,
    ): Int {
        val curr = currentItem ?: return -1

        val distancePerItem = estimateDistancePerItem()
        if (distancePerItem <= 0) {
            return curr.index
        }

        val distanceToCurrent = distanceToIndexSnap(curr.index)
        val distanceToNext = distanceToIndexSnap(curr.index + 1)

        if (abs(velocity) < 0.5f) {
            return when {
                distanceToCurrent.absoluteValue < distanceToNext.absoluteValue -> curr.index
                else -> curr.index + 1
            }.coerceIn(0, itemCount - 1)
        }

        val flingDistance = decayAnimationSpec.calculateTargetValue(0f, velocity)
            .coerceIn(-maximumFlingDistance, maximumFlingDistance).let { distance ->
                if (velocity < 0) {
                    (distance + distanceToNext).coerceAtMost(0f)
                } else {
                    (distance + distanceToCurrent).coerceAtLeast(0f)
                }
            }

        val flingIndexDelta = flingDistance / distancePerItem.toDouble()
        val currentItemOffsetRatio = distanceToCurrent / distancePerItem.toDouble()

        val indexOffset = (flingIndexDelta - currentItemOffsetRatio).roundToInt()

        return (curr.index + indexOffset).coerceIn(0, itemCount - 1).also { result ->
            SnapperLog.d {
                "determineTargetIndex. result: $result, current item: $curr, " + "current item offset: ${
                    "%.3f".format(
                        currentItemOffsetRatio
                    )
                }, " + "distancePerItem: $distancePerItem, " + "maximumFlingDistance: ${
                    "%.3f".format(
                        maximumFlingDistance
                    )
                }, " + "flingDistance: ${"%.3f".format(flingDistance)}, " + "flingIndexDelta: ${
                    "%.3f".format(
                        flingIndexDelta
                    )
                }"
            }
        }
    }

    private fun calculateItemSpacing(): Int = with(lazyListState.layoutInfo) {
        if (visibleItemsInfo.size >= 2) {
            val first = visibleItemsInfo[0]
            val second = visibleItemsInfo[1]
            second.offset - (first.size + first.offset)
        } else 0
    }

    private fun estimateDistancePerItem(): Float = with(lazyListState.layoutInfo) {
        if (visibleItemsInfo.isEmpty()) return -1f

        val minPosView = visibleItemsInfo.minByOrNull { it.offset } ?: return -1f
        val maxPosView = visibleItemsInfo.maxByOrNull { it.offset + it.size } ?: return -1f

        val start = min(minPosView.offset, maxPosView.offset)
        val end = max(minPosView.offset + minPosView.size, maxPosView.offset + maxPosView.size)

        return when (val distance = end - start) {
            0 -> -1f
            else -> (distance + calculateItemSpacing()) / visibleItemsInfo.size.toFloat()
        }
    }
}

private class LazyListSnapperLayoutItemInfo(
    private val lazyListItem: LazyListItemInfo,
) : SnapperLayoutItemInfo() {
    override val index: Int get() = lazyListItem.index
    override val offset: Int get() = lazyListItem.offset
    override val size: Int get() = lazyListItem.size
}

internal object SnapperLog {
    inline fun d(tag: String = "SnapperFlingBehavior", message: () -> String) {
        Log.d(tag, message())
    }
}

enum class TimeFormat {
    HOUR_24, AM_PM
}

data class Hour(
    val text: String, val value: Int, val index: Int
)

data class AmPmHour(
    val text: String, val value: Int, val index: Int
)

@RequiresApi(Build.VERSION_CODES.O)
internal fun localTimeToAmPmHour(localTime: LocalTime): Int {

    if (isBetween(
            localTime, LocalTime.of(0, 0), LocalTime.of(0, 59)
        )
    ) {
        return localTime.hour + 12
    }

    if (isBetween(
            localTime, LocalTime.of(1, 0), LocalTime.of(11, 59)
        )
    ) {
        return localTime.hour
    }

    if (isBetween(
            localTime, LocalTime.of(12, 0), LocalTime.of(12, 59)
        )
    ) {
        return localTime.hour
    }

    if (isBetween(
            localTime, LocalTime.of(13, 0), LocalTime.of(23, 59)
        )
    ) {
        return localTime.hour - 12
    }

    return localTime.hour
}

@RequiresApi(Build.VERSION_CODES.O)
fun isBetween(localTime: LocalTime, startTime: LocalTime, endTime: LocalTime): Boolean {
    return localTime in startTime..endTime
}

fun amPmHourToHour24(amPmHour: Int, amPmMinute: Int, amPmValue: AmPmValue): Int {

    return when (amPmValue) {
        AmPmValue.AM -> {
            if (amPmHour == 12 && amPmMinute <= 59) {
                0
            } else {
                amPmHour
            }
        }

        AmPmValue.PM -> {
            if (amPmHour == 12 && amPmMinute <= 59) {
                amPmHour
            } else {
                amPmHour + 12
            }
        }
    }
}

data class Minute(
    val text: String, val value: Int, val index: Int
)

data class AmPm(
    val text: String, val value: AmPmValue, val index: Int?
)

enum class AmPmValue {
    AM, PM
}

@RequiresApi(Build.VERSION_CODES.O)
fun amPmValueFromTime(time: LocalTime): AmPmValue {
    return if (time.hour > 11) AmPmValue.PM else AmPmValue.AM
}

object WheelPickerDefaults {
    @Composable
    fun selectorProperties(
        enabled: Boolean = true,
        borderColor: Color = MaterialTheme.colorScheme.primary.copy(0.7f),
    ): SelectorProperties = DefaultSelectorProperties(
        enabled = enabled,
        borderColor = borderColor,
    )
}

interface SelectorProperties {
    @Composable
    fun enabled(): State<Boolean>

    @Composable
    fun color(): State<Color>

}

@Immutable
internal class DefaultSelectorProperties(
    private val enabled: Boolean,
    private val borderColor: Color,
) : SelectorProperties {

    @Composable
    override fun enabled(): State<Boolean> {
        return rememberUpdatedState(enabled)
    }

    @Composable
    override fun color(): State<Color> {
        return rememberUpdatedState(borderColor)
    }
}

internal sealed class SnappedTime(val snappedLocalTime: LocalTime, val snappedIndex: Int) {
    data class Hour(val localTime: LocalTime, val index: Int) : SnappedTime(localTime, index)
    data class Minute(val localTime: LocalTime, val index: Int) : SnappedTime(localTime, index)
}


@OptIn(ExperimentalSnapperApi::class)
@Composable
fun calculateAnimatedAlpha(
    lazyListState: LazyListState, snapperLayoutInfo: SnapperLayoutInfo, index: Int, rowCount: Int
): Float {

    val distanceToIndexSnap = snapperLayoutInfo.distanceToIndexSnap(index).absoluteValue
    val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }.value
    val viewPortHeight = layoutInfo.viewportSize.height.toFloat()
    val singleViewPortHeight = viewPortHeight / rowCount

    return if (distanceToIndexSnap in 0..singleViewPortHeight.toInt()) {
        1.2f - (distanceToIndexSnap / singleViewPortHeight)
    } else {
        0.2f
    }
}

@OptIn(ExperimentalSnapperApi::class)
private fun calculateSnappedItemIndex(snapperLayoutInfo: SnapperLayoutInfo): Int? {
    var currentItemIndex = snapperLayoutInfo.currentItem?.index

    if (snapperLayoutInfo.currentItem?.offset != 0) {
        if (currentItemIndex != null) {
            currentItemIndex++
        }
    }
    return currentItemIndex
}

