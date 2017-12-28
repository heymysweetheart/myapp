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
## Code the right way
In theory, every method need to be tested. But in reality, most of codes are difficult to test because there are 
codes of accessing resources (either from database or from another web service) and codes of computation mingled 
together in one method. The reason why one should decouple these two codes is because the developer should focus on 
the logic of the computation, where most bugs occur most of the time.
## How to test controller
