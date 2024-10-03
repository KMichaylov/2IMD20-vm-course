function asString(val)
{
	return "" + val;
}

function asInt(val)
{
	eval("sl", "function __dynamic__valueAsInt() { return " + val + "- 0; }");
	return __dynamic__valueAsInt();
}

function main()
{
	println(typeOf(asString(1)));
	println(typeOf(asString("a string")));
	println(typeOf(asString("a boolean")));
	println(typeOf(asInt("1")));
	println(typeOf(asInt("not a number")));
}
