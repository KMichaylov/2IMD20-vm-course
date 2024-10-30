function main() {
	
	s = "a_string";
	println(getSize(s));
	println(hasSize(s));

	s2 = subString(s, 0, 3);
	println(s2);
	println(typeOf(s2));
	println(getSize(s2));
	println(hasSize(s2));

	s3 = subString(32, 42, 33);
}