# Pokedex-Android

Pokedex-Android was developed to showcase various modern Android development toolkits.

## Project Tech Stack:
  * Architecture: MVVM (View - ViewModle - Model), helps to keep project clean and maintainable by offering seperation of concerns.
  * Programming Language: Kotlin. 
  * Async Operations: Coroutines and Flow.
  * UI: Jetpack Compose - Is a declarative way of creating UIs in monder android development, recommented by google.
  * Local Database: Room persistence library to keep data when there is no internet available.
  * Dependency Injection(DI): Hilt is use
  * Network operations: Retrofit
  * Load Images from URl: Glide
  * KSP: Kotlin Symbol Processing API for code generation and analysis.
  * Navigate between screens: Type-save Navifation

## Basic Configurations:
  * compileSdk: 34
  * targetSdk: 34
  * minSdk: 28

## References:
  ### UI insperation:
 * Part of the UI used in the Pokemon Details screen is from figma shared UI which can be found here: https://www.figma.com/design/b5J7MDzB05ivkpGwjGJRyY/Pok%C3%A9dex-(Community)?node-id=314-3&t=qPncDaG0c3DWdIMN-0
  ### Pokedex-Android uses two APIs from which getting the required informations:
 * For pokemon information: https://pokeapi.co/
 * For pokemon image information: https://github.com/PokeAPI/sprites (Provides direct access to pokemon images)

## Example: 
<img src="https://github.com/user-attachments/assets/246900c8-ce96-4d05-b143-431238380dfa" width="200">
<img src="https://github.com/user-attachments/assets/4c2d37e9-4598-48c3-9423-5bd77047b622" width="200">
