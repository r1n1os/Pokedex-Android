# Pokedex-Android

Pokedex-Android was developed to showcase various modern Android development toolkits recommended by Google.

> [!NOTE]
> If you want to see the Flutter version of Pokedex, check out the [Pokedex-Flutter](https://github.com/r1n1os/pokedex-flutter) repository.
> 
## Project Tech Stack:
  * Architecture: [MVVM](https://developer.android.com/topic/architecture) (View - ViewModle - Model), helps to keep project clean and maintainable by offering seperation of concerns.
  * Programming Language: [Kotlin](https://kotlinlang.org/docs/android-overview.html). 
  * Async Operations: [Coroutines](https://developer.android.com/kotlin/coroutines).
  * UI: [Jetpack Compose](https://developer.android.com/compose) - Is a declarative way of creating UIs in monder android development, recommented by google.
  * Local Database: [Room](https://developer.android.com/training/data-storage/room) persistence library to keep data when there is no internet available.
  * Dependency Injection(DI): [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
  * Network operations: [Retrofit](https://square.github.io/retrofit/)
  * Load Images from URl: [Glide](https://bumptech.github.io/glide/int/compose.html)
  * [KSP](https://kotlinlang.org/docs/ksp-overview.html): Kotlin Symbol Processing API for code generation and analysis.
  * Navigate between screens: [Type-save Navigation - Compose Navigation](https://developer.android.com/guide/navigation/design/type-safety)

## Basic Configurations:
  * compileSdk: 34
  * targetSdk: 34
  * minSdk: 28
  * gradle: 8.10
  * kotlin: 2.0.20

## Example: 
<img src="https://github.com/user-attachments/assets/fe2cc945-359a-467e-8dbf-d01154f1898a" width="200" alt="Pokemon List Screen">
<img src="https://github.com/user-attachments/assets/5b8bfa52-933f-4813-b32a-214882954995" width="200" alt="Pokemon Details Screen">
<img src="https://github.com/user-attachments/assets/f7b81fd8-14ef-4b67-8b53-809c3189e0a7" width="200" alt="Pokemon Not Found Screen">

## References:
  ### UI insperation:
 * Part of the UI used in the Pokemon Details screen is from figma shared UI which can be found here: https://www.figma.com/design/b5J7MDzB05ivkpGwjGJRyY/Pok%C3%A9dex-(Community)?node-id=314-3&t=qPncDaG0c3DWdIMN-0
  ### Pokedex-Android uses two APIs from which getting the required information:
 * For pokemon information: https://pokeapi.co/
 * For pokemon image information: https://github.com/PokeAPI/sprites (Provides direct access to pokemon images)
