
import java.util.ArrayList;

public class BusStopSearch {
	private static TernarySearchTree searchTree = null;
	public static boolean isValid = false;
	
	public class TSTNode {
		public char value;
		public TSTNode left, middle, right;
		public boolean isEnd;

		TSTNode() {
			value = '\0';
			left = null;
			middle = null;
			right = null;
			isEnd = false;
		}

		TSTNode(char c) {
			value = c;
			left = null;
			middle = null;
			right = null;
			isEnd = false;
		}
	}

	public class TernarySearchTree {
		public TSTNode root = null;

		// generates tree from input file
		TernarySearchTree(ArrayList<Stops.Stop> stops) {
			root = new TSTNode();

			if (stops == null) {
				return;
			}
			
			int numStops = stops.size();
			for (int i=0; i<numStops; i++) {
				String currentStop = stops.get(i).stopName;
				if (currentStop!= null && !(currentStop.contentEquals(""))) {
					insert(currentStop);					
				}
			}
		}

		// insert new word into tree
		public void insert(String word) {
			if (word == null) {
				return;
			}
			String upperCaseWord = word.toUpperCase();
			root = insert(root, upperCaseWord.toCharArray(), 0);
		}

		private TSTNode insert(TSTNode node, char[] word, int pointer) {
			if (node == null)
				node = new TSTNode(word[pointer]);

			if (word[pointer] < node.value) {
				node.left = insert(node.left, word, pointer);
			} else if (word[pointer] > node.value) {
				node.right = insert(node.right, word, pointer);
			} else {
				if (pointer + 1 < word.length) {
					node.middle = insert(node.middle, word, pointer + 1);
				} else {
					node.isEnd = true;
				}
			}

			return node;
		}

		// Return is word is in tree
		public boolean search(String word) {
			if (word == null || word.length() == 0) {
				return false;
			}
			String upperCaseWord = word.toUpperCase();
			return search(root, upperCaseWord.toCharArray(), 0);
		}

		private boolean search(TSTNode node, char[] word, int pointer) {
			if (node == null) {
				return false;
			}

			if (word[pointer] < node.value) {
				return search(node.left, word, pointer);
			} else if (word[pointer] > node.value) {
				return search(node.right, word, pointer);
			} else {
				if (node.isEnd && pointer == word.length - 1) {
					return true;
				} else if (pointer == word.length - 1) {
					return false;
				} else {
					return search(node.middle, word, pointer + 1);
				}
			}
		}

		// Traverses the tree according to the prefix, and returns the last node it was
		// on
		public TSTNode getPrefixNode(TSTNode node, String prefix, int depth) {
			if (node == null || prefix == null || prefix.length() == 0) {
				return null;
			}

			char c = prefix.charAt(depth);
			if (c < node.value) {
				return getPrefixNode(node.left, prefix, depth);
			} else if (c > node.value) {
				return getPrefixNode(node.right, prefix, depth);
			} else if (depth < prefix.length() - 1) {
				return getPrefixNode(node.middle, prefix, depth + 1);
			} else
				return node;
		}

		// Traverse the tree from a given root and add words to the list
		public void getWordsWithPrefix(TSTNode root, StringBuilder prefix, ArrayList<String> matches) {
			if (root == null || matches == null || prefix == null || prefix.length() == 0) {
				return;
			}

			getWordsWithPrefix(root.left, prefix, matches);
			if (root.isEnd) {
				matches.add(prefix.toString() + root.value);
			}
			getWordsWithPrefix(root.middle, prefix.append(root.value), matches);
			prefix.deleteCharAt(prefix.length() - 1);
			getWordsWithPrefix(root.right, prefix, matches);
		}

	}

	BusStopSearch(ArrayList<Stops.Stop> stops) {
		searchTree = new TernarySearchTree(stops);
		isValid = true;
	}

	// returns if the stop name is in the tree. Full character match.
	public static boolean isInDataBase(String stopName) {
		if (stopName == null || stopName.length() == 0) {
			return false;
		}
		String upperCaseStopName = stopName.toUpperCase();
		return searchTree.search(upperCaseStopName);
	}

	// returns all stop names that contains the prefix provided.
	public static ArrayList<Stops.Stop> findStops(String prefix) {
		ArrayList<String> matches = new ArrayList<String>();
		if (prefix == null || prefix.length() == 0 || searchTree == null || searchTree.root == null) {
			return new ArrayList<Stops.Stop>();
		}

		// find prefix root node
		TSTNode prefixNode = searchTree.getPrefixNode(searchTree.root, prefix, 0);
		if (prefixNode == null) {
			return new ArrayList<Stops.Stop>();
		}
		if (prefixNode.isEnd) {
			matches.add(prefix);
		}

		// get all words starting from the prefix root node
		searchTree.getWordsWithPrefix(prefixNode.middle, new StringBuilder(prefix), matches);

		int numMatches = matches.size();
		ArrayList<Stops.Stop> stops = new ArrayList<Stops.Stop>(numMatches);
		for (int i=0; i<numMatches; i++) {
			// Gets the stop from the stop name...
			stops.add(i, Stops.stops.get(Stops.stopNameIndexMap.get(matches.get(i))));
		}
		
		return stops;
	}


}
