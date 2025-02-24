# Usage


### Gap
```kotlin linenums="1""

Column {
    Text("Compose Utils")
    Gap(30) // 30dp gap vertically
    Text("Hi, there")
}

Row {
    Text("Compose Utils")
    Gap(30) // 30dp gap horizontally
    Text("Hi, there")
}

```



### text
```kotlin  linenums="1""
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }

    CxButton(loading = loading) { //Do something }

    CxElevatedButton("Elevated Button") { loading=true}

    CxOutlinedButton("Outlined Button") { loading=false}

    CxTextButton("Text Button", loading = loading,
        onLoadingContent = {
            "Loading...".text.make()
        }) {
        "Button Clicked".toast(context)
    }

```



### CxButton
```kotlin  title="CxButton.kt" linenums="1""
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }

    CxButton(loading = loading) { //Do something }

    CxElevatedButton("Elevated Button") { loading=true}

    CxOutlinedButton("Outlined Button") { loading=false}

    CxTextButton("Text Button", loading = loading,
        onLoadingContent = {
            "Loading...".text.make()
        }) {
        "Button Clicked".toast(context)
    }

```


### CxBlurredText

```kotlin  title="CxBlurredText.kt" linenums="1""

Column {

    CxBlurredText("Hello World, I am Jihan. and I am a Software developer")
        Gap(20)
        CxBlurredText("And this is an example of a CxBlurredText text",
            TextStyle(
                fontSize = 24.sp,
                color = Cx.blue300,
                letterSpacing = 2.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Cx.blue700,
                    blurRadius = 5f
                )

            )
        )
    }

```



### CxSegmentedButton
```kotlin  linenums="1""
    var cx1 by remember { mutableIntStateOf(0) }
         CxSegmentedButton(
             buttonArray = listOf("Option 1", "Option 2", "Option 3"),
             currentItem = cx1,
             title = "Segmented Button",
             onSegmentSelected = {
                 cx1 = it
             }
         )

```


### CxSnackBar
```kotlin  linenums="1""
     val topSnackBarState = rememberCxSnackBarState()
    val bottomSnackBarState = remember{
        CxSnackBarState()
    }
    val floatingSnackBarState = remember{
        CxSnackBarState()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4579FF)
                ),
                onClick = { topSnackBarState.addMessage("This is a Top SnackBar") }
            ) {
                Text(text = "Top SnackBar")
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4579FF)
                ),
                onClick = { bottomSnackBarState.addMessage("This is a Bottom SnackBar") }
            ) {
                Text(text = "Bottom SnackBar")
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4579FF)
                ),
                onClick = { floatingSnackBarState.addMessage("This is a Floating SnackBar") }
            ) {
                Text(text = "Floating SnackBar")
            }
        }

        CxSnackBar(
            state = topSnackBarState,
            duration = 3000L,
            backgroundColor = Color(0xFF18B661),
            iconColor = Color(0xFFEEEEEE),
            iconSize = 28.dp,
            position = CxSnackBarPosition.Top,
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp,
            iconRes = lucide.check, // Replace with your image
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = Alignment.Top
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards = Alignment.Top
            ),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
        )

        CxSnackBar(
            state = bottomSnackBarState,
            duration = 3000L,
            position = CxSnackBarPosition.Bottom,
            backgroundColor = Color(0xFFE85039),
            iconColor = Color(0xFFEEEEEE),
            iconSize = 28.dp,
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp,
            iconRes = lucide.circle_check, // Replace with your image
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = Alignment.Bottom
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards = Alignment.Bottom
            ),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
        )

        CxSnackBar(
            state = floatingSnackBarState,
            position = CxSnackBarPosition.Float,
            duration = 3000L,
            iconRes = lucide.check, // Replace with your image
            backgroundColor = Color(0xFF69C3EB),
            iconColor = Color.White,
            iconSize = 28.dp,
            enterAnimation = fadeIn(),
            exitAnimation = fadeOut(),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            ),
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp
        )
    }

```



### CxSwingAnimation
```kotlin  linenums="1""
 

@Composable
fun SwingAnimationDemo() {
    CxSwingAnimation(
        modifier = Modifier
            .background(color = Color(0XFF1c8bd2))
            .size(width = 220.dp, height = 195.dp),
        boardImage =  Icons.Default.AccountBox,
        textContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 10.dp, top = 90.dp, end = 10.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Opening",
                    color = Color(0XFFFF3749),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal
                )
                Text(
                    text = "SOON!",
                    color = Color(0XFF111111),
                    fontSize = 29.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                )
            }
        },
        yOffset = (-28).dp,
        angleOffset = 3f
    )
}


```

### CxWheelTimePicker
```kotlin  linenums="1""
 
 @RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelTimePickerDemo() {
    var showSheet by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    if (showSheet) {
        CxWheelTimePicker(modifier = Modifier.fillMaxWidth(),
            titleStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
            ),
            doneLabelStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
                color = Color(0xFF007AFF),
            ),
            textColor = Color(0xff007AFF),
            timeFormat = TimeFormat.AM_PM,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                borderColor = Color.LightGray,
            ),
            rowCount = 5,
            size = DpSize(128.dp, 160.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight(600),
            ),
            onDismiss = {
                showSheet = false
            },
            onDoneClick = {
                selectedDate = timeToString(it, "hh:mm a")
                showSheet = false
            })
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                showSheet = true
            }) {
                Text(text = "Show BottomSheet")
            }
            Text(
                text = selectedDate,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}

```


### CxSimpleBottomNav
```kotlin  linenums="1""
 
 private val screen = listOf(
    SimpleBottomNavItem(
        icon = lucide.house, title = "Home"
    ), SimpleBottomNavItem(
        icon = lucide.search, title = "Search"
    ), SimpleBottomNavItem(
        icon = lucide.message_square, title = "Chat"
    ), SimpleBottomNavItem(
        icon = lucide.user_round, title = "Profile"
    )
)

@Composable
fun CxBottomNavDemo() {

    CenterBox {


        CxSimpleBottomNav(
            screens = screen,
            height = 80.dp,
            backgroundColor = Color(0xFFF1F1F1),
            selectedColor = Color(0xFF007AFF),
            unSelectedColor = Color(0XFF89788B),
            iconSize = 28.dp,
            selectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold
            ),
            unselectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif
            )
        )




        CxSimpleBottomNav(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            screens = screen,
            showDash = true,
            height = 80.dp,
            backgroundColor = Color(0xFF0B264F),
            selectedColor = Color(0xFFFFBB4E),
            unSelectedColor = Color(0XFFE3E3E3),
            iconSize = 28.dp,
            selectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold
            ),
            unselectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif
            ),
            shape = RoundedCornerShape(50.dp)
        )

        CxSimpleBottomNav(
            screens = screen,
            showDot = true,
            height = 80.dp,
            backgroundColor = Color(0xFF222222),
            selectedColor = Color(0xFF73D3BB),
            unSelectedColor = Color(0XFFFFFFFF),
            iconSize = 28.dp,
            dotBottomPadding = 3.dp,
            selectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold
            ),
            unselectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif
            ),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )

    }


}

```


### CxRollerEffectText
```kotlin  linenums="1""
 
 @Composable
fun RollerEffectTextDemo() {
    var count by remember {
        mutableIntStateOf(0)
    }

    CxRollerEffectText(
        count = count,
        style = TextStyle(
            color = Color(0xFF3E3E3E),
            fontSize = 32.sp
        ),
        modifier = Modifier.clickable{
            count-=1
        }
    )


    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4579FF)
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            count++
        }
    ) {
        Text(
            text = "Increment",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

```




### CxTypeWriterText
```kotlin  linenums="1""
 @Composable
fun TypewriterTextDemo() {

        val texts = listOf(
            "Welcome to my app!",
            "This is a cool typing effect",
            "With multiple features..."
        )

        CxTypewriterText(
            texts = texts,
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            ),
            typingSpeed = 100,
            delayBetweenTexts = 2000,
            onTextComplete = { index ->
                println("Completed typing text #${index + 1}")
            },
            onAllTextsComplete = {
                println("All texts have been displayed!")
            }
        )

}

```




### CxRangeSlider
```kotlin  linenums="1""
 
 @Composable
fun CxRangeSliderDemo() {
    CxRangeSliderWithPin(
        modifier = Modifier
            .padding(horizontal = 48.dp)
            .fillMaxWidth(),
        trackInActiveColor = Color(0xFFFACBD9),
        trackColor = Color(0xffEF286D),
        startPinCircleColor = Color(0xFFEF286D),
        endPinCircleColor = Color(0xFFEF286D),
        startPinColor = Color(0xFFEF286D),
        endPinColor = Color(0xFFEF286D),
        startPinTextStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        ),
        endPinTextStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        ),
        trackThickness = 4.dp,
        pinCircleSize = 10.dp,
        startProgress = 10f,
        endProgress = 50f,
        pinSpacing = 16.dp,
        pinWidth = 40.dp,
        pinHeight = 40.dp
    ) { startProgressValue, endProgressValue ->

    }
}

```



### CxProgress
```kotlin  linenums="1""
 
 Column{

 CxOrbitLoading()
 CxPulseLoading()
 CxNeonProgressLoader()

 }

```



### CxMultiStepLoader
```kotlin  linenums="1""
 @Composable
fun CxMultiStepLoaderDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        CxMultiStepLoader(
            modifier = Modifier
                .fillMaxSize(),
            size = 100.dp,
            strokeWidth = 16.dp,
            spacing = 5.dp,
            progressList = listOf(
                ProgressItem(
                    tag = "Calories",
                    progressColor = Color(0xFF00D4D6),
                    trackColor = Color(0xFF001D23),
                    progress = 70f,
                    animDuration = 1000
                ),
                ProgressItem(
                    tag = "Steps",
                    progressColor = Color(0xFF74F400),
                    trackColor = Color(0xFF002200),
                    progress = 60f,
                    animDuration = 2000
                ),
                ProgressItem(
                    tag = "Distance",
                    progressColor = Color(0xFFF45E9F),
                    trackColor = Color(0x992C0308),
                    progress = 50f,
                    animDuration = 3000
                ),

            )
        )
    }
}

```



### CxIndicators
```kotlin  linenums="1""
     CxIndicators(
                count = 5,
                size = 45,
                spacer = 4,
                selectedIndex = 0,
                selectedColor = Cx.blue700,
                unselectedColor = Cx.gray500
            ) {
                // Handle click event
            }

```



### CxExposedDropdownMenu
```kotlin  linenums="1""
 
 @Composable
fun ExposedDropdownDemo(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val list = remember { listOf("Item 1", "Item 2", "Item 3") }
        var text by remember { mutableStateOf("") }

        text.text.make()
        Gap(30)
        CxExposedDropdownMenu(list) { string, int ->
            text = "$string\nIndex:$int"
        }



    }
}

```


### CxExpandableCard
```kotlin  linenums="1""
 
 @Composable
fun ExpandableCardDemo() {

    Column { 
        
    // Standalone usage
    val state = rememberCxExpandableCardState()
    CxExpandableCard(
        title = "My Card",
        state = state
    ) {
        "This is the content of the card".text.make()
        Gap(10)  
        "This is the content of the card".text.make()
        Gap(10)
    }

        Gap(10)
    HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Gray))
Gap(10)
// As part of a group
    val groupState = rememberCxExpandableCardGroupState(count = 3)
    CxExpandableCard(
        title = "Card 1",
        state = groupState.getState(0)
    ) {
        "This is the content of the card".text.make()
        Gap(10)
        "This is the content of the card".text.make()
        Gap(10)
    }


        Gap(10)
        CxExpandableCard(
            title = "Card 2",
            state = groupState.getState(1)
        ) {
            "This is the content of the card".text.make()
            Gap(10)
            "This is the content of the card".text.make()
            Gap(10)
        }

        Gap(10)

        CxExpandableCard(
            title = "Card 3",
            state = groupState.getState(2)
        ) {
            "This is the content of the card".text.make()
            Gap(10)
            "This is the content of the card".text.make()
            Gap(10)
        }

}
    }


```



### CxExpandingFav
```kotlin  linenums="1""
 @Composable
fun ExpandingFavDemo() {
    var fabState by remember { mutableStateOf<FABState>(FABState.Collapsed) }

    Box(
        modifier = Modifier
            .width(200.dp)
            .height(400.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        CxExpandingFav(
            modifier = Modifier
                .wrapContentSize(),
            fabList = listOf(
                CxFABItem(
                    iconRes = lucide.plus, // Replace with your own drawables
                    iconSize = 42.dp,
                    label = "Write",

                    labelStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {

                    }
                ),
                CxFABItem(
                    iconRes = lucide.youtube, // Replace with your own drawables
                    iconSize = 42.dp,
                    label = "Media",
                    color = Color.Black,
                    labelStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {

                    }
                ),
                CxFABItem(
                    iconRes = lucide.mic, // Replace with your own drawables
                    iconSize = 42.dp,
                    label = "Speak",
                    color = Color.Black,
                    labelStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {

                    }
                )
            ),
            fabText = "Create Article",
            fabTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            fabState = fabState,
            itemSpacing = 18.dp,
            onClick = {
                fabState = fabState.toggleValue()
            }
        )
    }
}

```


### CxEditText
```kotlin  linenums="1""
 @Composable
fun EditTextDemo(modifier: Modifier = Modifier) {
    Column(Modifier.fillMaxSize()) {

        var name by remember { mutableStateOf("") }
        CxEditText(
            value = name,
            label = "Name",
            onValueChange = { name = it }
        )

        Gap(20)

        CxEditText(
            value = name,
            label = "Password",
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            shape = RoundedCornerShape(20),
            keyboardType = KeyboardType.Password,
        ){
            name = it
        }

        var address by remember { mutableStateOf("") }
        CxEditText(
            value = address,
            label = "Address",
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            leadingIcon = lucide.map,
            onValueChange = { address = it }
        )



        CxEditText(
            value = name,
            label = "Phone Number",
            shape = RoundedCornerShape(0.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            keyboardType = KeyboardType.Phone,
            onValueChange = { name = it }
        )



        CxEditText(
            value = name,
            label = "Username",
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            leadingIcon = lucide.at_sign,
            onValueChange = { name = it }
        )


        CxEditText(
            value = name,
            label = "Password",
            shape = RoundedCornerShape(20.dp),
            keyboardType = KeyboardType.Password,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            name = it
        }



    }
}

```


### CxContactList
```kotlin  linenums="1""
 @Composable
fun ContactListDemo() {
    val names =
        "Aurora, Aria, Asher, Benjamin, Bianca, Beatrix, Caleb, Clara, Chloe, Daniel, Delilah, Diana, Elijah, Emily, Evangeline, Finnegan, Freya, Fiona, Gabriel, Grace, Gwendolyn, Henry, Harper, Helena, Isaac, Isabella, Ivy, James, Jasmine, Juliette, Kieran, Katherine, Kai, Liam, Lily, Luna, Mason, Madison, Maya, Noah, Natalie, Nora, Oliver, Olivia, Octavia, Peter, Penelope, Phoebe, Quentin, Quinn, Ryan, Rachel, Rose, Samuel, Sophia, Scarlett, Thomas, Taylor, Thea, Uriah, Uma, Victor, Violet, Vanessa, William, Willow, Wren, Xavier, Xena, Yasmine, Yara, Zachary, Zoe, Zara"
    val contacts = names.split(", ").map { Contact(it,lucide.user_round) }

    CxContactList(
        contacts = contacts,
        scrollingBubbleColor = Color(0xFF73D3BB),
        scrollingBubbleTextStyle = MaterialTheme.typography.titleLarge.copy(
            fontSize = 18.sp,
            color = Color.White,
        ),
        alphabetScrollerTextStyle = TextStyle(
            fontSize = 11.sp,
            lineHeight = 21.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFFB5B5B5),
            textAlign = TextAlign.Center,
        ),
        charStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFF8B37F7),
        ),
        nameTextStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF333333),
        ),
        iconSize = 46.dp
    )
}

```


### CxColoredEffectText
```kotlin  linenums="1""
 @Composable
fun ColorEffectTextDemo() {
    CxColorEffectText(
        text = "Don’t allow people to dim your shine because they are blinded.",
        textStyle = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        colors = listOf(
            Color(0xFFD79B63),
            Color(0xFF58B9E6),
            Color(0xFF6D7AE0),
            Color(0xFFB962EE)
        )
    )
}

```


### CxIconCheck
```kotlin  linenums="1""
 /*** Simple Checkbox ***/
@Composable
fun IconCheckBoxDemo() {
    val checkedState = remember { mutableStateOf(false) }

    CxIconCheckBox(
        isChecked = checkedState.value,
        checkedIcon = lucide.smile,
        unCheckedIcon = lucide.angry,
        onCheckedChange = {
            checkedState.value = !checkedState.value
        }
    )
}

/*** Checkbox With Label ***/
@Composable
fun CheckboxWithLabelDemo() {
    val checkedState = remember { mutableStateOf(false) }

    CxIconCheckBoxWithLabel(
        isChecked = checkedState.value,
        checkedIcon = lucide.smile,
        unCheckedIcon = lucide.annoyed,
        onCheckedChange = {
            checkedState.value = !checkedState.value
        }
    )
}

/*** Grouped Checkbox ***/
@Composable
fun GroupedCheckboxDemo() {
    CxIconCheckBoxGrouped(
        options = listOf("Option 1", "Option 2", "Option 3"),
        checkedIcon = lucide.check,
        unCheckedIcon = lucide.dot,
        onSelectionChange = {

        }
    )
}

```


### CxOtpView
```kotlin  linenums="1""
 
 @Composable
fun OtpViewDemo() {
    var otpValue by remember {
        mutableStateOf("")
    }
    var showOtp by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CxOtpView(
            otpText = otpValue,
            otpCount = 6,
            showOtp = showOtp,
            showError = false,
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            textSize = 16.sp
        ) { text ->
            otpValue = text
        }


        Button(
            onClick = {
                showOtp = !showOtp
            }
        ) {
            Text(
                text = if (showOtp) {
                    "Hide OTP"
                } else {
                    "Show OTP"
                }
            )
        }

        val context = LocalContext.current
        AnimatedVisibility(otpValue.length==6) {
            Button(onClick = {
                "Submitted OTP: $otpValue".toast(context)
                otpValue = ""
            }) { Text("Submit") }
        }
    }
}

```




### CxPicker
```kotlin  linenums="1""
 @Composable
fun CxPicker() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        val values = remember { (1..9999).map { it.toString() } }
        val valuesPickerState = rememberPickerState()

        CxPicker(
            state = valuesPickerState,
            items = values,
            visibleItemsCount = 5,
            modifier = Modifier.fillMaxWidth(0.5f),
            textModifier = Modifier.padding(8.dp),
            textStyle = TextStyle(fontSize = 32.sp),
            dividerColor = Color(0xFFE8E8E8)
        )

        Text(
            text = "Result: ${valuesPickerState.selectedItem}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(500),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(0.5f)
                .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 8.dp))
                .padding(vertical = 10.dp, horizontal = 16.dp)
        )
    }
}

```



### CxColoredShadow
```kotlin  linenums="1""
 @Composable
fun ColoredShadowDemo() {

    Column {

    "Hello World".text.modifier(Modifier.coloredShadow(Color.Red)).make()

        Gap(10)
    "Hello World".text.modifier(Modifier.coloredShadow(
        color = Color.Blue,
        alpha = .3f,
        borderRadius = 10,
        shadowRadius = 15
    )).make()

    }
}

```





### CxLifecycleManager
```kotlin  linenums="1""
 @Composable
fun LifecycleExample() {


    CxLifecycleManager(onStart = { println("▶️ Component started") },
        onResume = { println("✳️ Component resumed") },
        onPause = { println("⏸️ Component paused") },
        onStop = { println("⏹️ Component stopped") },
        onRestart = { println("🔄 Component restarted") },
        onDestroy = { println("💢 Component destroyed") },
        onDispose = { println("🗑️ Component disposed") })
}

```


### CxEncryption
```kotlin  linenums="1""
             val aes = remember { CxEncryption(
                secretKey = "secretKey"
            ) }
            
            val encrypted = aes.encrypt("Hello World")
            
            val decrypted = aes.decrypt(encrypted)

``` 



### CxFilePicker
```kotlin  linenums="1""

@Composable
fun CxFilePickerDemo(modifier: Modifier = Modifier) {


    val selectedImage = remember { mutableStateOf<Uri?>(null) }

    // Picking single image
    val imagePicker = rememberCxFilePicker(PickerType.Single(FileType.Image)){uris ->
        //? since we are picking single image, we can safely assume that the first uri in the list is the selected image
        if (uris.isNotEmpty()){
            selectedImage.value = uris[0]
        }

    }

    // Picking multiple images
    val videPicker = rememberCxFilePicker(PickerType.Multiple(FileType.Video)){uris: List<Uri> ->
        //? handle multiple images here
    }

    // Collecting picked videos through flow
    val pickedVideos = videPicker.pickedFilesFlow.collectAsStateWithLifecycle()


    CxButton("Pick Image") {
        imagePicker.pick()
    }

    val scope = rememberCoroutineScope()
    CxButton("Pick Video") {
      scope.launch {
          val list = videPicker.pickAwait()
          // using coroutine scope to call pickAwait() function
      }
    }



}

```



### CxExpandableFab
```kotlin  linenums="1""
 
@Composable
fun CxExpandableFabDemo(modifier: Modifier = Modifier) {
    
    val itemList = remember { 
        listOf(
            CxFabItem(iconResId = lucide.shopping,"Shopping"){/*handle click*/},
            CxFabItem(iconResId = lucide.ruler,"Ruler"){/*handle click*/},
            CxFabItem(iconResId = lucide.check,"Check"){/*handle click*/},
        )
    }
    
   CxExpandableFab(
       items = itemList,
       icon = lucide.plus,
   )
    
    
}

```






### CxColorExtension
```kotlin  linenums="1""

Cx.blue700
Cx.red50,
Cx.yellow100
// etc.........

```


### CxStringExtension
```kotlin  linenums="1""
 fun main() {
    val sampleString = "hello World 123"

    // firstLetterUpperCase()
    println(sampleString.firstLetterUpperCase()) // "Hello world 123"

    // eliminateFirst()
    println(sampleString.eliminateFirst()) // "ello World 123"

    // eliminateLast()
    println(sampleString.eliminateLast()) // "hello World 12"

    // isEmpty
    println(sampleString.isEmpty) // false

    // validateEmail()
    val email = "test@example.com"
    println(email.validateEmail()) // true

    // isRtlLanguage()
    println(sampleString.isRtlLanguage()) // false

    // orEmpty
    val nullString: String? = null
    println(nullString.orEmpty) // ""

    // ifEmpty()
    println("".ifEmpty { "Empty String" }) // "Empty String"
    println("Not empty".ifEmpty { "Empty String" }) // "Not empty"

    // removeAllWhiteSpace()
    println("hello   World".removeAllWhiteSpace()) // "helloWorld"

    // isNotBlank
    println(sampleString.isNotBlank) // true

    // hidePartial()
    println(sampleString.hidePartial(0, 5)) // "***** World 123"

    // numCurrency
    println("1234.56".numCurrency) // "$1,234.56"

    // numCurrencyWithLocale()
    println("1234.56".numCurrencyWithLocale(Locale.UK)) // "£1,234.56"

    // allWordsCapitalize()
    println("hello world".allWordsCapitalize()) // "Hello World"

    // compareToIgnoringCase()
    println("hello".compareToIgnoringCase("HELLO")) // 0

    // insert()
    println(sampleString.insert("Java", 6)) // "hello JavaWorld 123"

    // prepend()
    println(sampleString.prepend("Hi, ")) // "Hi, hello World 123"

    // reverse()
    println(sampleString.reverse()) // "321  dlroW olleh"

    // isCreditCardValid()
    val cardNumber = "4532015112830366"
    println(cardNumber.isCreditCardValid()) // true

    // isNumber()
    println("1234.56".isNumber()) // true
    println("hello".isNumber()) // false

    // isDigit()
    println("5".isDigit()) // true
    println("12".isDigit()) // false

    // isLetter()
    println("a".isLetter()) // true
    println("1".isLetter()) // false

    // isSymbol()
    println("!".isSymbol()) // true
    println("A".isSymbol()) // false

    // filterChars()
    println("hello!@#world".filterChars()) // "helloworld"

    // toDate()
    val dateString = "2025-02-13"
    println(dateString.toDate()) // "Wed Feb 13 00:00:00 GMT 2025"

    // toDateString()
    println(dateString.toDateString()) // "February 13, 2025"

    // lowerCamelCase
    println("hello_world_example".lowerCamelCase) // "helloWorldExample"

    // upperCamelCase
    println("hello_world_example".upperCamelCase) // "HelloWorldExample"

    // capitalized
    println("hello".capitalized) // "Hello"

    // snakeCase
    println("HelloWorld".snakeCase) // "hello_world"

    // toEncodedBase64 (Requires API Level 26)
    // println("Hello".toEncodedBase64()) // "SGVsbG8="

    // toDecodedBase64 (Requires API Level 26)
    // println("SGVsbG8=".toDecodedBase64()) // "Hello"

    // utf8ToList
    println("Hello".utf8ToList) // [72, 101, 108, 108, 111]

    // utf8Encode
    println("Hello".utf8Encode.joinToString()) // "72, 101, 108, 108, 111"

    // formatDigitPattern
    println("1234567890".formatDigitPattern(4)) // "1234 5678 90"

    // formatDigitPatternEnd
    println("1234567890".formatDigitPatternEnd(4)) // "1234 5678 90"
}


```
