//	During function execution, override it

function InternalOverride()
{
	eval("sl", "function InternalOverride() {}");

	println("Hello world");
}


function MainOverride()
{
	eval("sl", "function main() {}");

	println("Main is now gone");
}

function main()
{
	InternalOverride();
	
	// Hello world is gone
	InternalOverride();	
}
