function for(base, limit, increase, call) {
	index = base;
	if (isExecutable(base)) {
		index = base();
	}


	while (1 == 1) {
		if (isExecutable(limit)) {
			if (limit(index)) {
			} else {
				break;
			}
		} else {
			if (index >= limit) {
				break;
			}
		}
		call(index);
		if (isExecutable(increase)) {
			index = increase(index);
		} else {
			index = index + increase;
		}
	}
}

function test(index) {
	println(index);
}

function two() {
	return 2;
}

function smallerThanEleven(i)
{
	return i < 11;
}

function increaseByTwo(i)
{
	return i + 2;
}

function main()
{
	println("for i = 0; i < 4; i += 1");
	for(0, 4, 1, test);
	println("for i = two(); smallerThanEleven(i); i = increaseByTwo(i)");
	for(two, smallerThanEleven, increaseByTwo, test);
}
