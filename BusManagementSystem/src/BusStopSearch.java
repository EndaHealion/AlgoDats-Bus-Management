
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BusStopSearch {

	Trie searchTree = null;

	public class TrieNode {
		public HashMap<Character, TrieNode> children;
		public String value;
		public boolean isEnd;

		public boolean containsKey(char c) {
			return children.containsKey((Character) c);
		}

		void add(char c, TrieNode node) {
			children.put((Character) c, node);
		}

		TrieNode get(char c) {
			return children.get((Character) c);
		}

		TrieNode() {
			children = new HashMap<Character, TrieNode>();
			value = "";
			isEnd = false;
		}
	}

	public class Trie {
		public TrieNode root = null;

		Trie(String fileLocation) {
			root = new TrieNode();

			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(fileLocation));
				// read line to get rid of column descriptors in CSV file...

				String line = reader.readLine();
				if (line == null)
					return; // if line is null then we have an error(no data)... @TEMP: Handle later

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

					// Insert word into tree...
					insert(stopName.toString());

					line = reader.readLine();
				}

				reader.close();
			} catch (IOException e) {
				System.out.println("Could not open file at location: " + fileLocation);
			}
		}

		public void insert(String word) {
			if (root == null) {
				return;
			}

			TrieNode node = root;
			for (int i = 0; i < word.length(); i++) {
				char currentChar = word.charAt(i);
				if (!node.containsKey(currentChar)) {
					node.add(currentChar, new TrieNode());
				}
				node = node.get(currentChar);
			}
			node.isEnd = true;
		}

		public TrieNode searchPrefix(String word) {
			if (root == null) {
				return null;
			}

			TrieNode node = root;
			for (int i = 0; i < word.length(); i++) {
				char currentChar = word.charAt(i);
				if (node.containsKey(currentChar)) {
					node = node.get(currentChar);
				} else {
					return null;
				}
			}

			return node;
		}

		public boolean isInTree(String word) {
			TrieNode node = searchPrefix(word);
			if (node == null) {
				return false;
			} else {
				return node.isEnd;
			}
		}

		public boolean startsWith(String prefix) {
			TrieNode node = searchPrefix(prefix);
			return node != null;
		}
	}

	BusStopSearch(String fileLocation) {
		searchTree = new Trie(fileLocation);
	}

	public boolean isInDataBase(String stopName) {
		return searchTree.isInTree(stopName);
	}

}
