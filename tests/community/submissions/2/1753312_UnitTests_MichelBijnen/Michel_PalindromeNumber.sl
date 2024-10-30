function null() {
}

function modulo(a, b) {
    if (b == 0) {
        return null();
    }

    while (a >= b) {
        a = a - b;
    }
    return a;
}

function isPalindrome(n) {
    original = n;
    reversed = 0;
    while (n > 0) {
        digit = modulo(n, 10);
        reversed = reversed * 10 + digit;
        n = (n - digit) / 10;
    }
    return original == reversed;
}

function main() {
    println(isPalindrome(1337));
    println(isPalindrome(1337331));
    println(isPalindrome(4242424));
    println(isPalindrome(424242));
    println(isPalindrome(69696));
}