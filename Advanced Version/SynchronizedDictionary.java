/**
 * Filename: SynchronizedDictionary - synchronized version of the SortedArrayDictionary
 * Assignment: Final Project
 * Version: 5/18/21
 * Note: I did not make this file, I am reusing this file that was made by the professor
 * from my data structures course where he gave us this file to use for our dictionary implementations.
 * This can also be found in the textbook for the CSC-273 course
 */

// Import statements
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SynchronizedDictionary<K extends Comparable<? super K>,V>
{
    private Entry<K,V>[] dictionary;    //array of unsorted entries
    private int numberOfEntries;
    private boolean integrityOK = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    public SynchronizedDictionary()
    {
        this(DEFAULT_CAPACITY);
    }

    public SynchronizedDictionary(int initialCapacity)
    {
        integrityOK = false;
        checkCapacity(initialCapacity);

        Entry<K,V>[] tempDictionary = (Entry<K,V>[])new Entry[initialCapacity];
        dictionary = tempDictionary;

        numberOfEntries = 0;
        integrityOK = true;
    }

    //helper method to ensure we are not trying to create an array that is too large
    private void checkCapacity(int capacity)
    {
        if (capacity >= MAX_CAPACITY)
            throw new IllegalStateException("Attempted to create dictionary whose capacity exceeds max allowed value.");

    }

    //checkIntegrity() - helper method to ensure dictionary is okay to work with
    private void checkIntegrity()
    {
        if (!integrityOK)
            throw new SecurityException("Data is corrupt.");
    }

    //ensureCapacity() - doubles the size of the array list if it is full
    //  precondition - checkIntegrity() has been called
    private void ensureCapacity()
    {
        int capacity = dictionary.length;
        if (numberOfEntries >= capacity)
        {
            int newCapacity = 2 * capacity;
            checkCapacity(newCapacity);
            dictionary = Arrays.copyOf(dictionary, newCapacity);
        }
    }

    /** Adds a new entry to this dictionary. If the given search key already
     exists in the dictionary, replaces the corresponding value.
     @param key    An object search key of the new entry.
     @param value  An object associated with the search key.
     @return  Either null if the new entry was added to the dictionary
     or the value that was associated with key if that value
     was replaced. */
    public synchronized V add(K key, V value)
    {
        checkIntegrity();
        if ((key == null) || (value == null))
            throw new IllegalArgumentException("Cannot add null to this dictionary.");
        else {
            V result = null;

            int keyIndex = locateIndex(key);
            if ((keyIndex < numberOfEntries) &&
                    (key.equals(dictionary[keyIndex].getKey())))
            {
                //key found; so replace and return old entry
                result = dictionary[keyIndex].getValue();
                dictionary[keyIndex].setValue(value);
            } else { // key not found, so add as new entry
                makeRoom(keyIndex); //need to make room for the new entry

                dictionary[keyIndex] = new Entry<>(key,value);
                numberOfEntries++;
                ensureCapacity();
            }

            return result;
        }
    }

    //helper method to locate the index at which key was found;
    // if not found, will return the location that SHOULD contain the key
    private int locateIndex(K key)
    {
        int index = 0;
        while ((index < numberOfEntries) &&
                key.compareTo(dictionary[index].getKey()) > 0)
            index++;

        return index;
    }

    //helper method to make room for a new entry at a given index by shifting
    //  entries towards the back of the array
    //Precondition:  keyIndex is valid; list length is the old length (before add)
    private void makeRoom(int keyIndex)
    {
        //start from the back
        for (int index = numberOfEntries - 1; index >= keyIndex; index--)
            dictionary[index+1] = dictionary[index];
    }

    /** Removes a specific entry from this dictionary.
     @param key  An object search key of the entry to be removed.
     @return  Either the value that was associated with the search key
     or null if no such object exists. */
    public synchronized V remove(K key)
    {
        checkIntegrity();
        V result = null;
        int keyIndex = locateIndex(key);

        //key was found...
        if (keyIndex < numberOfEntries &&
                key.equals(dictionary[keyIndex].getKey()))
        {
            result = dictionary[keyIndex].getValue();

            //call helper method to remove and close up gap
            removeArrayEntry(keyIndex);

            numberOfEntries--;
        }
        //if not found, result will still be null
        return result;
    }

    //helper method to remove an entry at a given index by shifting array
    // entries towards the entry to be removed
    private void removeArrayEntry(int keyIndex)
    {
        for (int fromIndex = keyIndex+1; fromIndex < numberOfEntries; fromIndex++)
            dictionary[fromIndex-1] = dictionary[fromIndex];

        dictionary[numberOfEntries - 1] = null;

    }

    /** Retrieves from this dictionary the value associated with a given
     search key.
     @param key  An object search key of the entry to be retrieved.
     @return  Either the value that is associated with the search key
     or null if no such object exists. */
    public V getValue(K key)
    {
        checkIntegrity();
        V result = null;
        int keyIndex = locateIndex(key);

        //key was found...
        if (keyIndex < numberOfEntries &&
                key.equals(dictionary[keyIndex].getKey()))
        {
            result = dictionary[keyIndex].getValue();
        }

        return result;
    }

    /** Sees whether a specific entry is in this dictionary.
     @param key  An object search key of the desired entry.
     @return  True if key is associated with an entry in the dictionary. */
    public boolean contains(K key)
    {
        return getValue(key) != null;
    }

    /** Creates an iterator that traverses all search keys in this dictionary.
     @return  An iterator that provides sequential access to the search
     keys in the dictionary. */
    public Iterator<K> getKeyIterator()
    {
        return new KeyIterator();
    }

    /** Creates an iterator that traverses all values in this dictionary.
     @return  An iterator that provides sequential access to the values
     in this dictionary. */
    public Iterator<V> getValueIterator()
    {
        return new ValueIterator();
    }

    /** Sees whether this dictionary is empty.
     @return  True if the dictionary is empty. */
    public boolean isEmpty()
    {
        return (numberOfEntries == 0);
    }

    /** Gets the size of this dictionary.
     @return  The number of entries (key-value pairs) currently
     in the dictionary. */
    public int getSize()
    {
        return numberOfEntries;
    }

    /** Removes all entries from this dictionary. */
    public void clear()
    {
        for (int i = 0; i < numberOfEntries; i++)
            dictionary[i] = null;

        numberOfEntries = 0;
    }

    private class Entry<K,V>
    {
        private K key;
        private V value;

        private Entry(K searchKey, V dataValue)
        {
            key = searchKey;
            value = dataValue;
        }

        private K getKey()
        {
            return key;
        }

        private V getValue()
        {
            return value;
        }

        private void setValue(V dataValue)
        {
            value = dataValue;
        }
    }

    private class KeyIterator implements Iterator<K>
    {
        private int currentIndex;

        private KeyIterator()
        {
            currentIndex = 0;
        }

        public boolean hasNext()
        {
            return currentIndex < numberOfEntries;
        }

        public K next()
        {
            K result = null;

            if (hasNext())
            {
                Entry<K,V>  currentEntry = dictionary[currentIndex];
                result = currentEntry.getKey();
                currentIndex++;
            } else
                throw new NoSuchElementException();

            return result;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private class ValueIterator implements Iterator<V>
    {
        private int currentIndex;

        private ValueIterator()
        {
            currentIndex = 0;
        }

        public boolean hasNext()
        {
            return currentIndex < numberOfEntries;
        }

        public V next()
        {
            V result = null;

            if (hasNext())
            {
                Entry<K,V> currentEntry = dictionary[currentIndex];
                result = currentEntry.getValue();
                currentIndex++;
            } else
                throw new NoSuchElementException();

            return result;

        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
