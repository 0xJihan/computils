# Computils

[![JitPack](https://jitpack.io/v/0xJihan/computils.svg)](https://jitpack.io/#0xJihan/computils)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

Computils is an Android library designed to streamline the development process by offering a collection of predefined composable components, customizable UI elements, and utility packages. It reduces boilerplate code, improves productivity, and allows developers to focus on building features rather than dealing with repetitive tasks.

## 🚀 Features

- **Predefined Composable Components** - Ready-to-use UI components for rapid development.
- **Customizable** - Easily tweak components to fit your app's design and requirements.
- **Utility Packages** - Helpful utility functions to simplify everyday tasks.
- **Extension Functions** - Improve code readability and enhance app functionality.
- **Easy to Use** - Intuitive API design for quick integration.
- **Less Boilerplate** - Write less code while maintaining high-quality, reusable components.

---

## 📦 Installation

To include Computils in your project, follow these steps:

### Step 1: Add JitPack Repository

Add the following to your `settings.gradle` file:

```gradle
repositories {
    maven("https://jitpack.io")
}
```

### Step 2: Add Dependency

Include the Computils dependency in your **module-level** `build.gradle` file:

```gradle
implementation("com.github.0xJihan:computils:v1.0.4")
```


---

## 📖 Usage

Computils provides various UI components and utilities to simplify Android development. Below are some examples:

### ✅ Gap

```kotlin
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
}
```

### 🔧 Text

```kotlin
@Composable
fun StyledTextUsage() {
    Column(modifier = Modifier.padding(16.dp)) {

        // 1. Simple usage of StyledText
        "Hello, Jetpack Compose!".text.make()

        // 2. Changing text size
        "Large Text".text.size(24).make()

        // 3. Changing text color
        "Colored Text".text.color(Color.Red).make()

        // 4. Making text bold
        "Bold Text".text.bold().make()

        // 5. Applying a modifier
        "Padded Text".text.modifier(Modifier.padding(8.dp)).make()
    }
}

// Example of using the toast function in an activity or Composable:
fun showToast(context: Context) {
    "Hello, Toast!".toast(context)
}
```

For more usage examples, visit the [Documentation](https://0xjihan.github.io/computils).

---

## 🤝 Contributing

We welcome contributions! If you'd like to improve Computils, feel free to:

- Open an issue
- Fork the repo and submit a pull request
- Improve documentation

---

## 📄 License

Computils is licensed under the [MIT License](https://opensource.org/licenses/MIT).

---

## 📬 Contact

For any queries, feel free to reach out:

- Email: [jihankhan966@gmail.com](mailto:jihankhan966@gmail.com)
- Email: [apkrahad@gmail.com](mailto:apkrahad@gmail.com)

Happy Coding! 🚀
