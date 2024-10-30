function f1()
{
	f4();
	f2();
}

function f2()
{
	f3();
}

function f3()
{
	println(stacktrace());
}

function f4()
{
	f3();
}

function main()
{
	f1();
}
