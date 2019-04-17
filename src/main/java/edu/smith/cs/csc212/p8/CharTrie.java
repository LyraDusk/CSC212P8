package edu.smith.cs.csc212.p8;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This is a Character Trie that stores Strings!
 * @author jfoley
 *
 */
public class CharTrie extends AbstractSet<String> {
	/**
	 * The nodes don't actually store characters in them - the links do.
	 */
	Node root = new Node();
	/**
	 * This gets updated to account for the size.
	 */
	int size = 0;

	public void insert(String word) {
		LinkedList<Character> chars = new LinkedList<>();
		for (char c : word.toCharArray()) {
			chars.add(c);
		}
		root.insert(chars);
		size++;
	}
	
	public boolean contains(Object o) {
		String word = (String) o;
		LinkedList<Character> chars = new LinkedList<>();
		for (char c : word.toCharArray()) {
			chars.add(c);
		}
		return root.find(chars);
	}
	
	/**
	 * Every node in a Trie may have many links (to future letters) AND it may be the "terminal" state of a word.
	 */
	private static class Node {
		/**
		 * Did a word end at this node?
		 */
		boolean terminal;
		/**
		 * This is an array of links.
		 */
		Node[] links;
		
		/**
		 * Construct a new node (with space for 27 links!).
		 */
		public Node() {
			this.terminal = false;
			this.links = new Node[27];
		}
		
		/**
		 * This maps a character to it's index in our array of links.
		 * @param c (a letter, a-z or a hyphen.)
		 * @return 0-25 for a-z and - for 26
		 */
		public int getLinkIndex(char c) {
			char lower = Character.toLowerCase(c);
			if (lower == '-') {
				return this.links.length - 1;
			}
			if (lower > 'z' || lower < 'a') {
				return -1;
			}
			return lower - 'a';	
		}
		
		/**
		 * Insert a word in this trie (or a suffix of a word, because recursion).
		 * @param chars - a list of characters that used to be a word.
		 */
		public void insert(LinkedList<Character> chars) {
			if (chars.isEmpty()) {
				this.terminal = true;
			} else {
				char c = chars.pollFirst();
				int link = getLinkIndex(c);
				if (link == -1) {
					throw new RuntimeException("Bad Character: "+ c);
				}
				if (links[link] == null) {
					links[link] = new Node();
				}
				links[link].insert(chars);
			}
		}
		
		/**
		 * Find a word in this trie (or a suffix of a word, because recursion).
		 * @param chars - a list of characters that used to be a word.
		 * @return true if this trie contains that word.
		 */
		public boolean find(LinkedList<Character> chars) {
			if (chars.isEmpty()) {
				return this.terminal;
			} else {
				int link = getLinkIndex(chars.pollFirst());
				if (link == -1) {
					return false;
				}
				if (links[link] == null) {
					return false;
				}
				return links[link].find(chars);
			}
		}
		
		/**
		 * Incomplete method to compute how many nodes are in this Trie. Recursive.
		 * @return the count of nodes that exist in the Trie, starting from here.
		 */
		public int countNodes() {
			int count = 1;
			for (Node n : links) {
				if (n != null) {
					int num = n.countNodes();
					count += num;
				}
			}
			return count;
		}
	}
	
	/**
	 * How do you count the nodes in this Trie?
	 * Recursion!
	 * @return the number of nodes in the trie.
	 */
	public int countNodes() {
		return root.countNodes();
	}

	/**
	 * We would need to create an object that kept the recursion state around.
	 * We will talk about depth-first search (part of the solution to this) next week.
	 */
	@Override
	public Iterator<String> iterator() {
		throw new UnsupportedOperationException("Trie Traversal is really hard.");
	}

	/**
	 * Just keeping track of the size is cheap. We could also count terminal nodes...
	 */
	@Override
	public int size() {
		return size;
	}
}
