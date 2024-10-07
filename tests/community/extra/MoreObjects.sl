function main() {
	
	o = new();

	o[1] = "xxx";
	println(o[1]);

	println(hasProperty(o, "x"));
	println(hasProperty(o, 1));

	o.x = 23;
	println(hasProperty(o, "x"));
	println(o.x);

	println(deleteProperty(o, "x"));
	println(hasProperty(o, "x"));
	println(o.x);
}
