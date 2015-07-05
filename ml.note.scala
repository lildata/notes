Object MachineLearning {

	val terminology = Map(

"Observations" 
	-> "Iterms or entities used for learning or evaluation, e.g. emails for spam classification",

"Features" 
	-> "Attributes (typically numeric) used to represent an observation, e.g., length, data, etc."

"Labels" 
	-> "Values / categories assigned to objervations, e.g., spam / not-spam",

"Training data" 
	-> "Observations used to train a learning algorithm, e.g., a set of emails along with their labels.",

"Test data" 
	-> "Observations used to evaluate a learning algorithm",

"Supervised learning" 
	-> "Learning from labeled observations (labels 'teach' the algorithm to learn mapping from observation to labels)",

"Unsupervised learning" 
	-> "Learning from unlabeled observations (the algorithm must find latent structure from features alone).\nCan be a goal in itself like discover hidden patterns or exploratory data analysis.\nUnsupervised learning can be used as a preprocessing step for a downstream supervised task."

	)
	
	val tips = Set(
"Fitting training data does not guarantee generalization (e.g., lookup table)"
	)

}

/**
 * Describe how an algorithm respond to change in input size
 * also refered as complexisty
 */
object BigONotation {
val complexities = Map(

"O(1) complexity" 
	-> "Constant time algorithms perform the same # of operations every time they are called. Similarly constant space algorithms require a fixed alout of storage every time they are called.",

"O(n) complexity" 
	-> "Linear time algoithm perform a # operation proportial to the number of inputs. e.g., adding two n-dimensional vectors.\n Same logic for linear space algorithms.",

"O(n2) complexity" 
	-> "Quadratic time algorithms perform a # of operations propotianl to the square of the # of inputs. E.g., outer product of two n-dimentinal vectors require O(n2) multiplication operations.\n Same logic for linear space algorithms."

}
