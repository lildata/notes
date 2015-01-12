notes
=====

# Basics

## Anonymous functions :

    def cube(x: Int): Int = x * x * x

becomes :

    (x: Int) => x * x * x


## case class

1. You can create new instances without new
2. Each case class has a companion object with an apply method
3. automatic auto values for parameters
automatic toString, equals, hashcode
4. copy method

but...

overhead in bytecode
can't inherit a case class from another

Good for value objects (immutable)
Bad for service objects (with state)


    abstract class Term
    case class Var(name: String) extends Term
    case class Fun(arg: String, body: Term) extends Term
    case class App(f: Term, v: Term) extends Term
    ...
    def printTerm(term: Term) {
        term match {
            case Var(n) => print(n)
            case Fun(x, b) => print("^" + x + ".")
            printTerm(b)
            case App(f, v) => Console.print("(")
            printTerm(f)
            print(" ")
            printTerm(v)
            print(")")
        }
    }


## Evaluation strategies (call by value / name)

call-by-value (advantage : evaluate every function argument only once)
call-by-name (advantage : an argument is not evaluated if the corresponding parameter is not used in the evaluation of the function body)

Scala normally use call-by-value

    def log(msg: String) //call by value (default)
    def log(msg: => String) //call by name

The same distinction apply to definitions :
The def form is "by-name", its right hand side is evaluated on each use
The val form is "by-value", its right hand side is only evaluated at defintion

###Require

    class uneClasse(x: Int, y: Int) {
        require(y > 0, ”denominator must be positive”)
        ...
    }

require is a predefined function. It takes a condition and an optional message string.
If the condition passed to require is false, an
IllegalArgumentException is thrown with the given message string.

### @BeanProperty

This is a special annotation which is essentially read by the Scala compiler to mean “generate a getter and setter for this field”:

    import scala.reflect.BeanProperty
    
    class Person {
        @BeanProperty
        var name = "Daniel Spiewak"
    }


###Parallelism != Concurrency

Parallelism is addressed in Scala with :
1. Collections
Parallel collections
Distributed collections (run on top of Hadoop, Spark, etc.)
2. Parallel DSLs

Concurrency is addressed in Scala with :
1. Actors
2. Software transactional memory
3. Futures
1. 2. 3. -> Akka

we are bad at managing concurrency
because we are optimistic animals, we want to achieve things





###override (redefine)

    abstract class Base {
        def foo = 1
        def bar: Int
    }
    
    class Sub extends Base {
        override def foo = 2
        def bar = 3
    }
    
### Diff between val and def

concerns only the evaluation
A -val- is evaluated when the object is first initialized whereas a -def- is evaluated each time it is referenced
Exceptions

if you don' t know which exception to throw you can always use :
throw new Error('bla bla bla')

##Pattern matching

We get pattern matching in Scala by the way of case classes

there is no fall through to the next alternative (so no need to break;)
An analog to a companion object in Java is having a class with static methods. In Scala you would move the static methods to a Companion object.

One of the most common uses of a companion object is to define factory methods for class.
A second common use-case for companion objects is to create extractors for the class.

### Unit
Unit is a subtype of scala.AnyVal. There is only one value of type Unit, (), and it is not represented by any object in the underlying runtime system. A method with return type Unit is analogous to a Java method which is declared void.
Recursive fonctions nées an explicit retourne type un scala

## Singleton

### infix operator
a unary operation is an operation with only one
operand, i.e. a single input.

Persistent data structures :

## Scala top Types

scala.Any (base type of all types)
scala.AnyRef (alias of java.lang.Object)
scala.AnyVal (base type of all primitive types)

scala.Nothing is a subtype of every other type :
- to signal abnormal termination
- as an element type of an empty collection

scala.Null
- the type of null
- or any ref value in the hierarchy

# Functions

## Functions as objects
functions are treated as objects in scala
A => B is just an abbreviation for the class

    scala.Function1[A, B]

defined as :
    
    trait Function1

##Type bounds

S <: T means S is a subtype of T
S >: T means S is a super type of T
S >: T1 <: T2 you can also mix upper and lower bounds
In Scala immutable types can usually be covariant but mutable ones cannot

###Vector vs List

Vector have a much better random access perf profil and is also good for bulk operations
But if access patter have recursive structures list is better

##For comprehensions

They are syntactic sugar for composition of multiple operations withforeach, map, flatMap, filter or withFilter. 
withFilter is a lazy variant of filter. You can think about withFilter as a variant of filter that does not produce an intermediate list.

For yield can be used as SQL queries ! ;-)

    def mapFun[T, U](xs: List[T], f: T => U): List[U]
    = for (x <- xs) yield f(x)
    
    def flatMap[T, U](xs: List[T], f: T => Iterable[U]): List[U]
    = for (x <- xs; y <- f(x)) yield y
    
    def filter[T](xs: List[T], p: T => Boolean): List[T]
    = for (x <- xs if p(x)) yield x





    for {
        n <- 1 to 3
        m <- 1 to n
    } yield n * m

is equivalent to :

    ( 1 to 3 ) flatMap( n => ( 1 to n ) map( m => n * m ) )


##Streams

Streams are like Lists but their tail is only evaluated on demand (lazy Lists)
be carefull with cons : :: produce a list but #:: produce a stream

as a matter of fact in the cons method for Streams the tail parameter is a "by name" parameter
for List it is a normal "call by value" parameter

## Eg of very simple Scala expression :

    val f: String => String = { case "ping" => "pong"}

##Named arguments

##Collections

++
:+
+:



### scala.collection.JavaConversions

####Two ways conversions :
scala.collection.Iterable <=> java.lang.Iterable

scala.collection.Iterable <=> java.util.Collection

scala.collection.Iterator <=> java.util.{ Iterator, Enumeration }

scala.collection.mutable.Buffer <=> java.util.List

scala.collection.mutable.Set <=> java.util.Set

scala.collection.mutable.Map <=> java.util.{ Map, Dictionary }

scala.collection.mutable.ConcurrentMap <=> java.util.concurrent.ConcurrentMap


####One way conversions :
scala.collection.Seq => java.util.List

scala.collection.mutable.Seq => java.util.List

scala.collection.Set => java.util.Set

scala.collection.Map => java.util.Map

java.util.Properties => scala.collection.mutable.Map[String, String]


###String interpolation

    val name = "Fred"
    println(s"My name is $name.")
    
    println(s"First Name: ${u.firstName.getOrElse("not assigned")}")
    
    println(f"


### Enums

#### Using scala.Enumeration

    object Margin extends Enumeration {
        type Margin = Value
        val TOP, BOTTOM, LEFT, RIGHT = Value
    }


    Margin.values foreach println
    

#### Using traits as Enums

    trait Margin
      case object TOP extends Margin
      case object RIGHT extends Margin
      case object BOTTOM extends Margin
      case object LEFT extends Margin





