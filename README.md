# ISS-next-passes-predictions

App written in Kotlin that shows upcoming ISS passes for a particular location

### Android Jetpack Components:
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Navigation Component](https://developer.android.com/topic/libraries/architecture/navigation/) -> Navigation between fragments
- [Data Binding](https://developer.android.com/topic/libraries/data-binding)

### Connection with API
- [Retrofit](https://square.github.io/retrofit/) -> Network Calls
- [OkHttp](https://square.github.io/okhttp) -> HTTP Client
- [Moshi](https://github.com/square/moshi) -> JSON Library
- [Retrofit Scalars Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/scalars) -> A Converter which supports converting strings and both primitives and their boxed types to text/plain bodies.
- [Stetho](https://github.com/facebook/stetho) -> Debug bridge. Used to create an interceptor with OkHttp.

### Testing
- [JUnit4](https://github.com/junit-team/junit4)
- [Mockito](https://github.com/mockito/mockito)
