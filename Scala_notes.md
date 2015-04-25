notes
=====

# Basics

## Anonymous functions :

```scala
def cube(x: Int): Int = x * x * x
```

becomes :

```scala
(x: Int) => x * x * x
```


## Extractor Objects (unapply)
http://docs.scala-lang.org/tutorials/tour/extractor-objects.html



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



```scala
abstract class Term
case class Var(name: String) extends Term
case class Fun(arg: String, body: Term) extends Term
case class App(f: Term, v: Term) extends Term
...
def printTerm(term: Term) {
term match {
case Var(n) =>
print(n)
case Fun(x, b) =>
print("^" + x + ".")
printTerm(b)
case App(f, v) =>
Console.print("(")
printTerm(f)
print(" ")
printTerm(v)
print(")")
}
}
```


## Evaluation strategies (call by value / name)

call-by-value (advantage : evaluate every function argument only once)
call-by-name (advantage : an argument is not evaluated if the corresponding parameter is not used in the evaluation of the function body)

Scala normally use call-by-value

```scala
def log(msg: String) //call by value (default)
def log(msg: => String) //call by name
```

The same distinction apply to definitions :
The def form is "by-name", its right hand side is evaluated on each use
The val form is "by-value", its right hand side is only evaluated at defintion


###Try type & Error Handling

Using flatMap to avoid Try[] nesting :

```scala
def inputStreamForURL(url: String): Try[InputStream] = 
	parseURL(url).flatMap { u => Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
}
```

the for comprehension : 

```scala
import scala.io.Source
def getURLContent(url: String): Try[Iterator[String]] =
  for {
    url <- parseURL(url)
    connection <- Try(url.openConnection())
    is <- Try(connection.getInputStream)
    source = Source.fromInputStream(is)
  } yield source.getLines()
```

####Recovering from a Failure

see `recover` and `recoverWith`


###Require

```scala
class uneClasse(x: Int, y: Int) {
require(y > 0, ”denominator must be positive”)
...
}
```

require is a predefined function. It takes a condition and an optional message string.
If the condition passed to require is false, an
IllegalArgumentException is thrown with the given message string.

### @BeanProperty

This is a special annotation which is essentially read by the Scala compiler to mean “generate a getter and setter for this field”:

```scala
import scala.reflect.BeanProperty

class Person {
@BeanProperty
var name = "Daniel Spiewak"
}
```


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

```scala
abstract class Base {
def foo = 1
def bar: Int
}

class Sub extends Base {
override def foo = 2
def bar = 3
}
```

Diff between val and def

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

###Pattern matching anonymous function
is an anonymous function that is defined as a block consisting of a sequence of cases, surrounded as usual by curly braces, but without a match keyword before the block.

*Just make sure that for all possible inputs, one of your cases matches so that your anonymous function always returns a value. Otherwise, you will risk a MatchError at runtime.*

```scala
val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) :: ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil
```

- Classic way to do :
```scala
def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] = wordFrequencies.filter(wf => wf._2 > 3 && wf._2 < 25).map(_._1)
```
- With pattern matching anonymous function :
```scala
def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] = wordFrequencies.filter { case (_, f) => f > 3 && f < 25 } map { case (w, _) => w }
```

### Unit
Unit is a subtype of scala.AnyVal. There is only one value of type Unit, (), and it is not represented by any object in the underlying runtime system. A method with return type Unit is analogous to a Java method which is declared void.
Recursive fonctions need an explicit return type in scala

### Nothing
http://www.scala-lang.org/api/current/index.html#scala.Nothing

Nothing is a subtype of every other type

It is useful to signal abnormal termination

or 

As an element type of empty collections : Set[Nothing]


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

```scala
scala.Function1[A, B]
```

defined as :

```scala
trait Function1
```

##Type bounds

S <: T means S is a subtype of T
S >: T means S is a super type of T
S >: T1 <: T2 you can also mix upper and lower bounds
In Scala immutable types can usually be covariant but mutable ones cannot

###Vector vs List
Vector have a much better random access perf profil and is also good for bulk operations
But if access patter have recursive structures list is better

##For comprehensions
They are syntactic sugar for composition of multiple operations with foreach, map, flatMap, filter or withFilter. 
withFilter is a lazy variant of filter. You can think about withFilter as a variant of filter that does not produce an intermediate list.

For yield can be used as SQL queries ! 

```scala
def mapFun[T, U](xs: List[T], f: T => U): List[U]
= for (x <- xs) yield f(x)

def flatMap[T, U](xs: List[T], f: T => Iterable[U]): List[U]
= for (x <- xs; y <- f(x)) yield y

def filter[T](xs: List[T], p: T => Boolean): List[T]
= for (x <- xs if p(x)) yield x
```

1) Translation 1 :
```scala
for (x <- e1) yield e2
```
into 
```scala
e1.map(x => e2)
```

2) Translation 2 :
```scala
for (x <- e1 if f; s) yield e2
```
into 
```scala
for (x <- e1.withFilter(x => f); s) yield e2
```

3) Translation 3 :
```scala
for (x <- e1; y <- e2; s) yield e3
```
into 
```scala
e1.flatMap(x => for (y <- e2; s) yield e3)
```


```scala
for {
n <- 1 to 3
m <- 1 to n
} yield n * m
```

is equivalent to :

```scala
( 1 to 3 ) flatMap( n => ( 1 to n ) map( m => n * m ) )
```




##Streams

Streams are like Lists but their tail is only evaluated on demand (lazy Lists)
be carefull with cons : :: produce a list but #:: produce a stream

as a matter of fact in the cons method for Streams the tail parameter is a "by name" parameter
for List it is a normal "call by value" parameter

## Eg of very simple Scala expression :

```scala
val f: String => String = { case "ping" => "pong"}
```

##Named arguments

##Collections

++
:+
+:



### scala.collection.JavaConversions

####Basics

```scala
import scala.collection.JavaConversions._

val names = List("Jack","Jill")
val wrapper = JavaConversions.seqAsJavaList(names)
AJavaClass.methodUsingJavaList(wrapper)
```
	
####Using implicit conversion

JavaConversions.seqAsJavaList is declared as an implicit method so we can do :

```scala
import scala.collection.JavaConversions._

val names = List("Jack","Jill")
AJavaClass.methodUsingJavaList(names)
```

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

```scala
val name = "Fred"
println(s"My name is $name.")
    
println(s"First Name: ${u.firstName.getOrElse("not assigned")}")
    
println(f"
```

### Enums

#### Using scala.Enumeration

```scala
object Margin extends Enumeration {
    type Margin = Value
    val TOP, BOTTOM, LEFT, RIGHT = Value
}

Margin.values foreach println
```    

#### Using traits as Enums

```scala
trait Margin
  case object TOP extends Margin
  case object RIGHT extends Margin
  case object BOTTOM extends Margin
  case object LEFT extends Margin
```

#Extra

##Scala Graph

###Info

The default Graph implementation is based on scala.collection.mutable.HashMap thus having a reasonably good performance.

Throughout the library we use the terms :
- node as a synonym to vertex 
- edge as a generic term for hyperedge
- line (undirected edge)
- arc (directed edge)

In most cases you need to import the following:

```scala
import scalax.collection.Graph // or scalax.collection.mutable.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._
```








