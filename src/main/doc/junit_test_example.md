## Junit
Here are some example that I collected form daily work.
## dependency
We add mockito and powermock to mock dependencies and static calls or method call with void return type.
## mockito
Mockito helps us a lot when working on unit test of a specific bean which has lots of other dependent beans. Taking 
the service bean as example, it has dependent DAO beans which would cause trouble if we only focus on the unit test 
of the target service. In this case, we use mockito. A great tool.
### @Mock
We can use `@Mock` annotation and `@InjectMocks` to mimic the dependency the dependent beans.
### @Spy
Difference between `@Spy` and `@Mock` is there must be a real object created when you use `@Spy`, so that we can mock
 part of the behaviours of the real object, thus retaining the rest of the original behaviours. Because there must be
  the real dependent object created, it's more difficult than using `@Mock`.
