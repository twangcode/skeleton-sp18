import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String emptyStr = "";
        String oneCharStr = "A";
        String truePalindrome = "noon";
        String anotherTruePalindrome = "ABCDCBA";
        String falsePalindrome = "good";

        assertTrue(palindrome.isPalindrome(emptyStr));
        assertTrue(palindrome.isPalindrome(oneCharStr));
        assertTrue(palindrome.isPalindrome(truePalindrome));
        assertTrue(palindrome.isPalindrome(anotherTruePalindrome));
        assertFalse(palindrome.isPalindrome(falsePalindrome));
    }

    @Test
    public void testIsPalindromeByComparator() {
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertFalse(palindrome.isPalindrome("big", offByOne));
    }
}
