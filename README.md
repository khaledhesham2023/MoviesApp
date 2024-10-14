# Movies App:

Movies App is a mobile application with features of showing a list of movies sorted according to
either popularity or top-rated movies, showing more details of the selected movie as well as adding
these movies to favorites.

## Demo:

![Demo Image](https://github.com/khaledhesham2023/MoviesApp/blob/master/app/src/main/res/assets/demo.gif)




## Architecture:

Using concepts of clean architecture by uncle Bob.
![Demo Image](https://github.com/khaledhesham2023/MoviesApp/blob/master/app/src/main/res/assets/architecture.png)



## Technologies:

1. [Clean Architecture](https://developer.android.com/topic/architecture): Using Uncle Bob's concepts of clean architecture. 
2. [Retrofit](https://square.github.io/retrofit/): A type-safe HTTP client used for API calls and mapping JSON response values into data classes properties.
3. [Glide](https://github.com/bumptech/glide): A library for loading image urls into ImageViews. 
4. [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): For displaying large datasets from either network or local cache into pages.
5. [Navigation Component](https://developer.android.com/jetpack/androidx/releases/navigation): for navigation between fragments in a single activity.
6. [DI (Dagger & Hilt)](https://dagger.dev/hilt/): For reducing boilerplate code and improving modularity of code by creating single instances that will be used across the entire application.
7. [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines): For running long-running tasks in background threads to reduce heavy load on main thread and avoid app freezing.
8. [MVVM & LiveData](https://www.geeksforgeeks.org/mvvm-model-view-viewmodel-architecture-pattern-in-android/): For managing UI components state according to incoming data from data sources.
9. [Room](https://developer.android.com/jetpack/androidx/releases/room) for obtaining data from local cache.
10. [The Movies Database Website](https://developer.themoviedb.org/): for API calls.
