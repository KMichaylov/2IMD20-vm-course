
function main() {
    signed_int_max = 2147483647;
    signed_int_max_plus_one = 2147483648;
    signed_int_min = 0 - 2147483648;
    signed_long_max = 9223372036854775807;
    signed_long_min = 0 - 9223372036854775808;
    signed_long_max_plus_one = 9223372036854775808;
    println(signed_int_max + 1);
    println(signed_int_max_plus_one - 1);
    println(signed_long_max + 1);
    println(signed_int_min - 1);
    println(signed_int_min - signed_int_min - signed_int_min - signed_int_min);
    println(2 * signed_int_max);
    println(2 * signed_long_max);
    println(2 * signed_int_min);
    println(signed_int_max * signed_int_max * signed_int_max);
    println(signed_int_min * signed_int_min * signed_int_min);
    println(signed_long_max * signed_long_max * signed_long_max);
    println(signed_long_min * signed_long_min * signed_long_min);
}
