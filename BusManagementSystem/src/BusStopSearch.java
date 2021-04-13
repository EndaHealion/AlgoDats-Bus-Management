
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

		// generates tree from input file... @TEMP: put the code that gets the stop
		// names in a function
		// with error handling
		TernarySearchTree(String fileLocation) {
			root = new TSTNode();

			// NOTE(Enda): This might be a bad idea because all buss stop locations will be
			//   in memory in two places at the same time...
			// Maybe revert back to doing this with the getStopNamesFromFile inlined
			ArrayList<String> stopNames = getStopNamesFromFile(fileLocation);

			// Insert words into tree...
			for (String stopName : stopNames) {
				insert(stopName.toString());
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

	BusStopSearch(String fileLocation) {
		searchTree = new TernarySearchTree(fileLocation);
	}

	// returns if the stop name is in the tree. Full character match.
	public boolean isInDataBase(String stopName) {
		if (stopName == null || stopName.length() == 0) {
			return false;
		}
		String upperCaseStopName = stopName.toUpperCase();
		return searchTree.search(upperCaseStopName);
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

	// Manipulate the stop name to make it more search able...
	// Move wb,nb,sb,eb from the start to the end
	private void makeStopSearchable(StringBuilder stopName) {
		if (stopName == null) {
			return;
		}
		
		int numPrefixChars = 3;
		if (stopName.length() > numPrefixChars) {
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
	}

	// get all stop names from file
	private ArrayList<String> getStopNamesFromFile(String fileLocation) {
		if (fileLocation == null) {
			return null;
		}
		
		ArrayList<String> stopNames = new ArrayList<String>();
		int currentLine = 1;
		int failedReads = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileLocation));

			// read line to get rid of column descriptors in CSV file...
			String line = reader.readLine();
			if (line == null) {
				System.out.println("[BusStopSearch] WARNING: No data in file: " + fileLocation);
				reader.close();
				return null;
			}

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

				StringBuilder stopName = new StringBuilder();
				if (commasSeen >= commasToSkip) {
					// Gather stop name
					while (currentChar < lineLen) {
						if (line.charAt(currentChar) == ',') {
							break;
						}
						stopName.append(Character.toUpperCase(line.charAt(currentChar)));
						currentChar++;
					}

					makeStopSearchable(stopName);
					stopNames.add(stopName.toString());
				} else {
					System.out.println("[BusStopSearch] WARNING: No stop name on line: " + currentLine);
					failedReads++;
				}

				currentLine++;
				line = reader.readLine();
			}

			if (failedReads > 0) {
				System.out.println("[BusStopSearch] WARNING: Could not read " + failedReads + " stop names from file.");
			}
			System.out.println("");

			reader.close();
			return stopNames;
		} catch (IOException e) {
			System.out.println("Could not open file at location: " + fileLocation);
			return null;
		}
	}
}
