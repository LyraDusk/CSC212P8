package edu.smith.cs.csc212.p8;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a simple HashSet that resolves collisions with a LinkedList.
 * @author jfoley
 *
 */
public class LLHash extends AbstractSet<String> {
	/**
	 * All the buckets, whether used or not.
	 */
	List<Bucket> buckets;
	
	/**
	 * A LLHash is of a fixed-size.
	 * @param numBuckets - the number of buckets to create.
	 */
	public LLHash(int numBuckets) {
		buckets = new ArrayList<>(numBuckets);
		for (int i=0; i<numBuckets; i++) {
			buckets.add(new Bucket());
		}
	}
	

	/**
	 * Add a value to this LLHash, if it is new.
	 * @param h - the string to add.
	 * @return true, because we could add it.
	 */
	@Override
	public boolean add(String h) {
		int hash = Math.abs(h.hashCode());
		int index = hash % this.buckets.size();
		Bucket bin = this.buckets.get(index);
		
		// Store value, if it is new:
		if (!bin.values.contains(h)) {
			bin.values.add(h);			
			// we added it.
			return true;
		} else {
			// it was already in this set.
			return false;
		}
	}
	
	/**
	 * Check whether the object is already stored. (Pre-Java 5, should be String).
	 * @param obj - really a String.
	 * @return true if it is in the appropriate bucket, false if not.
	 */
	@Override
	public boolean contains(Object obj) {
		int hash = Math.abs(obj.hashCode());
		int index = hash % this.buckets.size();
		Bucket bin = this.buckets.get(index);
		return bin.values.contains(obj);
	}

	/**
	 * Let us for-loop over all the values.
	 * @return an iterator over a copy of the current items.
	 */
	@Override
	public Iterator<String> iterator() {
		ArrayList<String> items = new ArrayList<>();
		for (Bucket b : this.buckets) {
			items.addAll(b.values);
		}
		return items.iterator();
	}

	/**
	 * Count up the number of items in this hash-set.
	 * @return the total number of stored strings.
	 */
	@Override
	public int size() {
		int count = 0;
		for (Bucket b : this.buckets) {
			count += b.values.size();
		}
		return count;
	}
	
	/**
	 * @return the number of buckets with more than one value.
	 */
	public int countCollisions() {
		int count = 0;
		for (Bucket b : this.buckets) {
			if (b.values.size() > 1) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @return the number of buckets with any value.
	 */
	public int countUsedBuckets() {
		int count = 0;
		for (Bucket b : this.buckets) {
			if (b.values.size() > 0) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * This is a bucket! It has a list of the values that collided here.
	 */
	private static class Bucket {
		List<String> values = new LinkedList<>();
	}
}
