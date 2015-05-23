[![Build Status](https://travis-ci.org/jsolve/oven.svg?branch=master)](https://travis-ci.org/jsolve/oven)
[![Coverage Status](https://coveralls.io/repos/jsolve/oven/badge.png)](https://coveralls.io/r/jsolve/oven)

The recommended way to get started using oven in your project is with a dependency management system – the snippet below can be copied and pasted into your build.
``` xml
<dependency>
	<groupId>pl.jsolve</groupId>
	<artifactId>oven</artifactId>
  <version>1.0.1</version>
</dependency>
```

---
Oven consists two mapping tools:
- `AnnotationDrivenMapper` for simple field to field mapping using annotations
- `ComplexMapper` for more advanced and complex mapping

# AnnotationDrivenMapper
Simple mapping can be resolved by using annotations. `AnnotationDrivenMapper` can be really useful when you have to map classes with a lot of common fields. Please take a few minutes to get familiar with the following annotations:    
- [@MappableTo](#mappableto)
- [@Map](#map)
  - [@Map(to=...) parameter](#mapto-parameter-optional)
  - [@Map(fromNested=...) parameter](#mapto-parameter-optional)
  - [@Map(of=...) parameter](#mapping-one-class-to-multiple)
  - [@Map(elementsAs=...) parameter](#mapelementsas-parameter-optional)
  - [@Map(keysAs=...) and @Map(valuesAs=...) parameters](#mapkeysas-valuesas-parameter-optional)
- [@Mappings](#mappings)
- [Writing custom annotations](#extending-annotationdrivenmapper-functionality)

***AnnotationDrivenMapper uses [TypeConverter](https://github.com/jsolve/sweetener/wiki/Type-converter) to convert fields to different types. Make sure you familiarize yourself with [supported conversions](https://github.com/jsolve/sweetener/wiki/Type-converter#supported-conversions) list.***
##  @MappableTo
Defines to what kind of class can be mapped. This annotation is intended to be used on types (classes, enums, interfaces) and takes target class as a parameter.
### Example
``` java
@MappableTo(HeroSnapshot.class) 
class Hero {
   // this `Hero` class can be mapped to `HeroSnapshot` class
}
```
``` java
@MappableTo({HeroSnapshot.class, HeroDTO.class}) 
class Hero {
   // this `Hero` class can be mapped to both `HeroSnapshot` and 'HeroDTO' class
}
```
## @Map
This annotation goes right above the field you want to copy. 
Parameters (all are optional):
 - `to` - the name of the target class' field. If not specified the source class' field name will be taken.
 - `of` - target class that this annotation applies. If not specified all classes specified in `@MappableTo` annotation will be taken.
 - `fromNested` - use when the value of source class' field is nested. Use dot character for each nest. 
 - `elementsAs` - use when working with generic collections, i.e. `List<String>`, `List<Integer>`, etc. 
 - `keysAs`, `valuesAs` - use when working with java.util.Map

Annotated fields via `@Map` will be copied to specified target class' fields. All of the parameters are well described with examples below.
### @Map(to=...) parameter (optional)
By the `to` parameter you can specify the name of target class field. If it is not specified the name of source class field will be used.
#### Example
Suppose we have `Hero` and `HeroSnapshot` classes that look like this:
``` java
class Hero {
	Long id; // we'd like this `id` to be set to HeroSnapshot `id` field
	String nickname; // we'd like this `nickname` to be set to HeroSnapshot `name` field
	String firstName;
	String lastName;
	// getters and setters
} 
```
``` java
class HeroSnapshot {
	Long id;
	String name;
}
```
You need to annotate source class (in our example `Hero` class) with `@MappableTo(<targetClass>)` and `@Map(to=<targetClassField>)` annotations like so:
``` java
@MappableTo(HeroSnapshot.class)
class Hero {
	@Map(to = "id") // `to` parameter is optional here as the target field name is the same as source but we've used it anyway
	Long id; // this `id` will be set to HeroSnapshot `id` field
	@Map(to = "name")
	String nickname; // this `nickname` will be set to HeroSnapshot `name` field
```
Then you can execute mapper on any `hero` object to map it to `HeroSnapshot` class. 
``` java
HeroSnapshot heroSnapshot = AnnotationDrivenMapper.map(hero, HeroSnapshot.class);
```

### @Map(fromNested=...) parameter (optional)
Mapping nested objects. `fromNested` - nested source field name. Use dot (".") for each nest, i.e. `address.postalCode`.
``` java
@Map( 	 	 	 		// copy value
	fromNested = "bar.name", 	// from `foo.bar.name`
	to = "bar" 			// to `bar`
)
Foo foo;
```

#### Example
The following example shows a `Person` class that will be mapped to `PersonSnapshot` class. 
``` java
class Person {
	Address address;
	// ... other fields
}
```
The `Address` class looks like this:
``` java
class Address {
	String street;
	City city;
	// ... getters and setters ...
}
```
And the target class - `PersonSnapshot`: 
``` java
class PersonSnapshot {
	String street;
}
```
We'd like to map `Person`'s street that is nested in `Address` to `PersonSnapshot`'s street. 
```
personSnapshot.street := person.address.street
```
In this situation we need to annotate `Person` with proper `@Map(fromNested=...)`:
``` java
@MappableTo(PersonSnapshot.class)
class Person {
	@Map(fromNested = "street", to = "street")
	Address address; // from nested Person's address.street to PersonSnapshot's street
}
```
Parameter `fromNested` defines which field in `address` is going to be copied. Parameter `to` - where it will be copied. You can execute `AnnotationDrivenMapper`:
``` java
Address address = new Address();
address.setCity(new City("Los Angeles"));
address.setStreet("Sunset Boulevard");
Person person = new Person();
person.setAddress(address);
PersonSnapshot personSnapshot = AnnotationDrivenMapper.map(person, PersonSnapshot.class)
```
and `personSnapshot.getStreet()` will return _Sunset Boulevard_. Just like we wanted to.

### @Map(elementsAs=...) parameter (optional)
This parameter is especially useful for collections mapping. Because generic types are erased at runtime, our mapper is not able to determine type of collection's element. 

#### Example
Suppose we have a class `StudentWithListOfGrades` that looks as follows:
``` java
class StudentWithListOfGrades {
   List<Grade> grades;
}
```
and a class `StudentWithListOfGradeSnapshots`:
``` java
class StudentWithListOfGradeSnapshots {
   List<GradeSnapshot> gradeSnapshots;
}
```
Assuming `Grade` is mappable to `GradeSnapshot`, properly annotated and working with AnnotationDrivenMapper you can map collection of `Grade` to collection of `GradeSnapshot` using annotations:
``` java
@MappableTo(StudentWithListOfGradeSnapshots.class)
class StudentWithListOfGrades {
   @Map(to = "gradeSnapshots", elementsAs = GradeSnapshot.class)
   List<Grade> grades;
}
```
and a class `StudentWithListOfGradeSnapshots`:
``` java
@MappableTo(StudentWithListOfGrades.class)
class StudentWithListOfGradeSnapshots {
   @Map(to = "grades", elementsAs = Grade.class)
   List<GradeSnapshot> gradeSnapshots;
}
```
This will also work when mapping array of `Grade` to collection of `GradeSnapshot` and vice versa.


### @Map(keysAs=..., valuesAs=...) parameter (optional)
This parameter is especially useful for java.util.Map collection mapping. Because generic types are erased at runtime, our mapper is not able to determine type of collection's element. 

#### Example
Suppose we have a class `StudentWithGrades` that looks like follows:
``` java
class StudentWithGrades {
   Map<Exam, Grade> grades;
}
```
and a class `StudentWithGradeSnapshots`:
``` java
class StudentWithGradeSnapshots {
   Map<ExamSnapshot, GradeSnapshot> gradeSnapshots;
}
```
Assuming `Grade` is mappable to `GradeSnapshot` and `Exam` to `ExamSnapshot`, fields are properly annotated and working with AnnotationDrivenMapper, I would like to map `StudentWithGrades` to `StudentWithGradeSnapshots`. You can annotate your class as follows:
``` java
@MappableTo(StudentWithGradeSnapshots.class)
class StudentWithGrades {

    @Map(to = "gradeSnapshots", keysAs = ExamSnapshot.class, valuesAs = GradeSnapshot.class)
    Map<Exam, Grade> grades;
}
```
After that the `grades` field of `StudentWithGrades` class can be mapped to `gradeSnapshots` of `StudentWithGradeSnapshots`.


## @Mappings 
If you have multiple fields where you want to copy a value from a single field then you need use `@Mappings` annotation with as many as you want `@Map` annotations as the paramteres.
``` java
@Mappings({ 
    // @Map annotations comma separated go here
})    
```
### Example with @Mappings( @Map(to=...), @Map(to=...) )
Let's say we have an `Order` class as follows:
``` java
class Order {
	String address;
}
```
and `OrderSnapshot` class:
``` java
class OrderSnapshot {
	String residentalAddress;
	String invoiceAddress;
}
```
We would like to map `Order` to `OrderSnapshot`. Assume that `residentalAddress` and `invoiceAddress` should contain the same address when mapping from `Order`. So you basically want to copy `address` from `Order` to both `residentalAdress` and `invoiceAddress` of `OrderSnapshot`. You cannot use two `@Map` annotations on the same field so you need to wrap them using `@Mappings`. Here's how our `Order` class should look like:
``` java
@MappableTo(OrderSnapshot.class) // Order can be mapped to OrderSnapshot
class Order {

	@Mappings({
		@Map(to = "residentalAddress"), // OrderSnapshot.residentalAddress := Order.address
		@Map(to = "invoiceAddress") // OrderSnapshot.invoiceAddress := Order.address 
	})
	String address;

}
```
Then you can use `AnnotationDrivenMapper`:
``` java
Order order = new Order();
order.setAddress("New York");
OrderSnapshot orderSnapshot = AnnotationDrivenMapper.map(order, OrderSnapshot.class);
```

In our case `AnnotationDrivenMapper.map()` will return `orderSnapshot` with both `residentalAdress` and `invoiceAddress` set to "New York".

### Another example with @Mappings( @Map(fromNested=...), @Map(fromNested=...) )
If you have multiple nested values on a single field that you want to map use `@Mappings` annotation.
``` java
@Mappings({ 
    // @Map annotations comma separated go here
})    
```
The following example shows a `Person` class that will be mapped to `PersonSnapshot` class. 
``` java
class Person {
	Address address;
	// ... other fields
}
```
The `Address` class looks like this:
``` java
class Address {
	String street;
	City city;
	// ... getters and setters ...
}
```
The `City` class:
``` java
class City {
	String name;
	long population;
	// ... getters and setters ...
}
```
And the target class - `PersonSnapshot`: 
``` java
class PersonSnapshot {
	String address;
	long population;
}
```
We want to map `Person`'s city name and city population to `PersonSnapshot`'s address and population. 
```
personSnapshot.address := person.address.city.name
personSnapshot.population := person.address.city.population
```
As you can see above the city name and population are both nested in `Address` field values. You can't use multiple annotations of the same type on a single field so you need to wrap them with `@Mappings`. It's easy. Take a look at the following mapping on `Person` class that does what we've just described:
``` java
@MappableTo(PersonSnapshot.class)
class Person {
    // multiple nested mappings are also valid
    @Mappings({
        @Map(fromNested = "city.name", to = "address"),
        @Map(fromNested = "city.population", to = "population")
    })
    Address address;
}
```
Let's go through the code:
- We've annotated `Person` class to be mapped to `PersonSnapshot` class. 
- The `Person`'s `name` field, that is nested in `city` field, that is nested in `address` field will be mapped to `PersonSnapshot`'s `address` field. 
- The `Person`'s `population` field, that is nested in `city` field, that is nested in `address` field will be mapped to `PersonSnapshot`'s `population` field. 

Imagine doing the same thing using getters and setters :)

## Mapping one class to multiple
You can also map one class to multiple classes using annotations. Just specify those classes in `@MappableTo` annotation like so `@MappableTo({ A.class, B.class, C.class })`. If an mapping annotation (`@Map`) is intended only for one target class (`A.class` or `B.class` or `C.class`) then you need to specify that class using an `of` parameter of that mapping annotation. If an mapping annotation is intended for all target classes then you don't need any additional parameters. Take a look at the following example.
#### Example
Let's say we have a `Hero` class that we want to be mappable to `HeroSnapshot`, `HeroDTO`.
``` java
class Hero {
   Long id;
   String nickname;
} 
```
``` java
class HeroSnapshot {
   Long id;
   String name;
}
```
``` java
class HeroDTO {
   Long id;
   String nickname;
}
```
To make `Hero` mappable to `HeroSnapshot` and `HeroDTO` we need to add `@MappableTo` annotation on the top of `Hero` class. Let's do that:
``` java
@MappableTo({ HeroSnapshot.class, HeroDTO.class })
class Hero {
   Long id;
   String nickname;
} 
```
We can see that both target classes contain `id` field. So the value from `Hero` must be copied to both of them. We will declare that using `@Map`:
``` java
@MappableTo({ HeroSnapshot.class, HeroDTO.class })
class Hero {
   @Map // `to` parameter will be set to "id" by default so we don't need to specify that
   Long id; // Hero.id value will be copied to HeroSnapshot.id, HeroDTO.id
   String nickname;
} 
```
`HeroSnapshot` and `HeroDTO` have different fields - `HeroSnapshot` has `name` field and `HeroDTO` has `nickname` field. We want to map both of them as follows:
```
HeroSnapshot.name := Hero.nickname
HeroDTO.nickname := Hero.nickname
```
To map those fields we need to use two separate `@Map` annotations with `of` parameter to indicate the target class.
``` java
@MappableTo({ HeroSnapshot.class, HeroDTO.class })
class Hero {
	@Map
	Long id;
	@Mappings({
		@Map(to = "name", of = HeroSnapshot.class), // HeroSnapshot.name := Hero.nickname
		@Map(to = "nickname", of = HeroDTO.class), //HeroDTO.nickname := Hero.nickname
	})
	String nickname;
}
```
That's it. You can now use `AnnotationDrivenMapper`:
``` java
Hero hero = aHero.withId(1L).withName("ironMan").build();
HeroSnapshot heroSnapshot = AnnotationDrivenMapper.map(hero, HeroSnapshot.class);
HeroDTO heroDTO = AnnotationDrivenMapper.map(hero, HeroDTO.class);
```

## Extending AnnotationDrivenMapper functionality
We understand that you can have needs that our [annotations](#annotationdrivenmapper) will not satisfy so we have left an open window for you. You can easily introduce your own annotation that `AnnotationDrivenMapper` will understand. To do so you only need to create a new class implementing `AnnotationMapping` and register that class using `AnnotationDrivenMapper.registerAnnotationMapping(annotationMapping)` - all annotation mappings [introduced](#annotationdrivenmapper) are implementing `AnnotationMapping` and are registered in the same way. 
### Example
Suppose we have a `Student` class with field `semester` (string):
``` java
class Student {
   String semester;
}
```
and `StudentSnapshot` class with field `semester` (integer):
``` java
class StudentSnapshot {
   int semester;
}
```
We want to map `Student` to `StudentSnapshot`. As you can see `Student`'s `semester` needs to be parsed to integer. We can do that using `ComplexMapper` or - better - create own `@MapParsingIntTo`:
``` java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MapParsingIntTo {
	String value();
}
```
and put it on the top of `Student`'s `semster`:
``` java
@MappableTo(StudentSnapshot.class)
class Student {
   @MapParsingIntTo("semester")
   String semester;
}
```
Currently `AnnotationDrivenMapper` does not understand our custom `@MapParsingIntTo` annotation and will skip it so we need to create an annotation mapping strategy - create a class that implements `AnnotatonMapping` interface.
``` java
public class MapParsingIntToAnnotationMapping implements AnnotationMapping {
	@Override
	public <S, T> void apply(S sourceObject, T targetObject) {
		List<Field> fieldsAnnotatedByOurAnnotation = Reflections.getFieldsAnnotatedBy(sourceObject, MapParsingIntTo.class);
		for (Field field : fieldsAnnotatedByOurAnnotation) {
			String targetFieldName = field.getAnnotation(MapParsingIntTo.class).value();
			String sourceObjectFieldValue = Reflections.getFieldValue(sourceObject, field.getName()).toString();
			Reflections.setFieldValue(targetObject, targetFieldName, Integer.parseInt(sourceObjectFieldValue));
		}
	}
}
```
The `apply` method above uses reflection to copy fields annotated by `@MapParsingIntTo` annotation to the field in `targetObject` with name specified by value parameter of `@MapParsingIntTo`. We only need to register that strategy and we are good to go:
``` java
AnnotationDrivenMapper.registerAnnotationMapping(new MapParsingIntToAnnotationMapping());
```
Now the AnnotationDrivenMapper will know how to deal with your own, custom `@MapParsingIntTo` annotations.
``` java
Student student = new Student();
student.setSemester("5");
StudentSnapshot studentSnapshot = AnnotationDrivenMapper.map(student, StudentSnapshot.class);
studentSnapshot.getSemester(); // will return 5 (int)
```
# ComplexMapper
For more complex mapping use `ComplexMapper` with a custom mapping strategy.     
Suppose we want to map `Hero` object to `HeroSnapshot`. The _name_ field of `HeroSnapshot` will be the concatenation of the _firstName_ and _lastName_ of `Hero`:
```
HeroSnapshot.name := Hero.firstName + " " + Hero.lastName
```
You need to create a new `ComplexMapper` with the following `MappingStrategy`:
``` java
ComplexMapper<Hero, HeroSnapshot> heroToHeroSnapshotMapper = new ComplexMapper<>(new MappingStrategy<Hero, HeroSnapshot>() {
   @Override
   public HeroSnapshot map(Hero source, HeroSnapshot target) {
      target.setName(source.getFirstName() + " " + source.getLastName());
      return target;
   }
});
```
And then use it like this:
``` java
Hero hero = aHero().withId(1L).withFirstName("Steve").withLastName("Rogers").withNickname("captainAmerica").build();
HeroSnapshot heroSnapshot = heroToHeroSnapshotMapper.map(hero);
```
`heroToHeroSnapshotMapper.map(hero)` will return `HeroSnapshot` object with name 'Steve Rogers'. If `Hero` class was also annotated for mapping - those annotated fields would also be set.
# ComplexMapper and AnnotationDrivenMapper working together
It is also possible to use `AnnotationDrivenMapper` for simple field to field mapping and `ComplexMapper` for the rest of the cases. Just annotate your class with `AnnotationDrivenMapper`'s [annotations](#annotationdrivenmapper) and create ComplexMapper similar to `heroToHeroSnapshotMapper` above. Keep in mind that `ComplexMapper` has higher priority and fields set in `MappingStrategy` will be decisive.
