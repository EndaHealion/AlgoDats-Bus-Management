
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

		public void insert(String word) {
			root = insert(root, word.toCharArray(), 0);
		}

		public TSTNode insert(TSTNode r, char[] word, int pointer) {
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

		private TSTNode getPrefixNode(TSTNode node, char[] word, int pointer) {
			if (node == null) {
				return null;
			}

			if (word[pointer] < node.value) {
				return getPrefixNode(node.left, word, pointer);
			} else if (word[pointer] > node.value) {
				return getPrefixNode(node.right, word, pointer);
			} else {
				if (node.value == word[pointer] && pointer == word.length - 1) {
					return node;
				} else if (pointer == word.length - 1) {
					return null;
				} else {
					return getPrefixNode(node.middle, word, pointer + 1);
				}
			}
		}

		public TSTNode getPrefixNode(TSTNode root, String prefix) {
			if (prefix == null || root == null) {
				return null;
			}

			System.out.println(prefix);

			TSTNode current = root;
			int prefixLen = prefix.length();
			for (int index = 0; index < prefixLen; index++) {
				char c = prefix.charAt(index);
				if (current.left != null && current.left.value == c) {
					current = current.left;
				} else if (current.middle != null && current.middle.value == c) {
					current = current.middle;
				} else if (current.right != null && current.right.value == c) {
					current = current.right;
				} else {
					return null;
				}
			}

			return current;
		}

	}

	BusStopSearch(String fileLocation) {
		searchTree = new TernarySearchTree(fileLocation);
	}

	public boolean isInDataBase(String stopName) {
		return searchTree.search(stopName);
	}

	private void getWordsWithPrefix(TSTNode x, StringBuilder prefix, ArrayList<String> matches) {
		if (x == null) {
			return;
		}

		getWordsWithPrefix(x.left, prefix, matches);
		if (x.isEnd) {
			matches.add(prefix.toString() + x.value);
		}
		getWordsWithPrefix(x.middle, prefix.append(x.value), matches);
		prefix.deleteCharAt(prefix.length() - 1);
		getWordsWithPrefix(x.right, prefix, matches);
	}

	public ArrayList<String> findStops(String prefix) {
		if (prefix == null || prefix.length() == 0 || searchTree == null || searchTree.root == null) {
			return null;
		}

		ArrayList<String> matches = new ArrayList<String>();
		TSTNode prefixNode = get(searchTree.root, prefix, 0);
		if (prefixNode == null) {
			return matches;
		}
		if (prefixNode.isEnd) {
			matches.add(prefix);
		}

		getWordsWithPrefix(prefixNode.middle, new StringBuilder(prefix), matches);

		return matches;
	}

	private TSTNode get(TSTNode node, String key, int d) {
		if (node == null || key.length() == 0) {
			return null;
		}

		char c = key.charAt(d);
		if (c < node.value) {
			return get(node.left, key, d);
		} else if (c > node.value) {
			return get(node.right, key, d);
		} else if (d < key.length() - 1) {
			return get(node.middle, key, d + 1);
		} else
			return node;
	}

}