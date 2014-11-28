notes
=====


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
    
    


### Enums

#### Using scala.Enumeration

    object Margin extends Enumeration {
        type Margin = Value
        val TOP, BOTTOM, LEFT, RIGHT = Value
    }
  
#### Using traits as Enums

    trait Margin
      case object TOP extends Margin
      case object RIGHT extends Margin
      case object BOTTOM extends Margin
      case object LEFT extends Margin





