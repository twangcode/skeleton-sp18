public class Palindrome {
    /** Returns a deque where the characters appear in the same order as in the string */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> charDeque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            charDeque.addLast(word.charAt(i));
        }
        return charDeque;
    }

    private boolean isPalinDromeDeque(Deque<Character> charDeque) {
        if (charDeque.size() <= 1) {
            return true;
        }
        if (charDeque.removeFirst() != charDeque.removeLast()) {
            return false;
        }
        return isPalinDromeDeque(charDeque);
    }

    private boolean isPalinDromeDeque(Deque<Character> charDeque, CharacterComparator cc){
        if (charDeque.size() <= 1) {
            return true;
        }
        if (!cc.equalChars(charDeque.removeFirst(), charDeque.removeLast())) {
            return false;
        }
        return isPalinDromeDeque(charDeque, cc);
    }

    /** Returns true if word is a palindrome. False otherwise */
    public boolean isPalindrome(String word) {
        Deque<Character> charDeque = wordToDeque(word);
        return isPalinDromeDeque(charDeque);
    }

    /** Returns true if word is a palindrome according to
     * provided comparator method. False otherwise */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> charDeque = wordToDeque(word);
        return isPalinDromeDeque(charDeque, cc);
    }
}
