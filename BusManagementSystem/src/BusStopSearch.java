
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BusStopSearch {

	TernarySearchTree searchTree = null;

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

		// generates tree from input file... @TEMP: put the code that gets the stop names in a function
		//                                            with error handling
		TernarySearchTree(String fileLocation) {
			root = new TSTNode();

			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(fileLocation));
				// read line to get rid of column descriptors in CSV file...

				String line = reader.readLine();
				if (line == null)
					return; // if line is null then we have an error(no data)... @TEMP: Handle later

				int stopCount = 0;
				line = reader.readLine();
				while (line != null) {
					// System.out.println(line);

					// Skip until "stop_name", which is in the third column...
					int commasToSkip = 2;
					int lineLen = line.length();
					int currentChar = 0;
					int commasSeen = 0;
					for (; commasSeen < commasToSkip && currentChar < lineLen; currentChar++) {
						if (line.charAt(currentChar) == ',') {
							commasSeen++;
						}
					}

					// Gather stop name
					StringBuilder stopName = new StringBuilder();
					while (currentChar < lineLen) {
						if (line.charAt(currentChar) == ',') {
							break;
						}
						stopName.append(line.charAt(currentChar));
						currentChar++;
					}

					// Manipulate the stop name to make it more search able...
					// Move: wb,nb,sb,eb from the start to the end
					if (stopName.length() > 3) {
						if (stopName.charAt(1) == 'B' && stopName.charAt(2) == ' ') {
							switch (stopName.charAt(0)) {
							case 'W':
							case 'N':
							case 'S':
							case 'E':
								stopName.delete(0, 3);
								stopName.append(' ');
								stopName.append(stopName.charAt(0));
								stopName.append('B');
								break;

							default:
							}
						}
					}

					// System.out.printf("[%d] %s\n", stopCount, stopName.toString());

					// Insert word into tree...
					insert(stopName.toString());

					stopCount++;
					line = reader.readLine();
				}

				reader.close();
			} catch (IOException e) {
				System.out.println("Could not open file at location: " + fileLocation);
			}
		}

		// insert new word into tree
		public void insert(String word) {
			root = insert(root, word.toCharArray(), 0);
		}

		private TSTNode insert(TSTNode r, char[] word, int pointer) {
			if (r == null)
				r = new TSTNode(word[pointer]);

			if (word[pointer] < r.value) {
				r.left = insert(r.left, word, pointer);
			} else if (word[pointer] > r.value) {
				r.right = insert(r.right, word, pointer);
			} else {
				if (pointer + 1 < word.length) {
					r.middle = insert(r.middle, word, pointer + 1);
				} else {
					r.isEnd = true;
				}
			}

			return r;
		}

		// Return is word is in tree
		public boolean search(String word) {
			return search(root, word.toCharArray(), 0);
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

		// Traverses the tree according to the prefix, and returns the last node it was on
		public TSTNode getPrefixNode(TSTNode node, String prefix, int depth) {
			if (node == null || prefix.length() == 0) {
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
			if (root == null || matches == null || prefix == null) {
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

	BusStopSearch(String fileLocation) {
		searchTree = new TernarySearchTree(fileLocation);
	}

	// returns if the stop name is in the tree. Full character match.
	public boolean isInDataBase(String stopName) {
		return searchTree.search(stopName);
	}

	// returns all stop names that contains the prefix provided.
	public ArrayList<String> findStops(String prefix) {
		ArrayList<String> matches = new ArrayList<String>();
		if (prefix == null || prefix.length() == 0 || searchTree == null || searchTree.root == null) {
			return matches;
		}

		// find prefix root node
		TSTNode prefixNode = searchTree.getPrefixNode(searchTree.root, prefix, 0);
		if (prefixNode == null) {
			return matches;
		}
		if (prefixNode.isEnd) {
			matches.add(prefix);
		}

		// get all words starting from the prefix root node
		searchTree.getWordsWithPrefix(prefixNode.middle, new StringBuilder(prefix), matches);

		return matches;
	}

}
