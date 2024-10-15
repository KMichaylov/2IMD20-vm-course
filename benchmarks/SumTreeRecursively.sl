// Calculate total sum of tree, recursively
function sumTree(tree) {
    leftSum = 0;
    rightSum = 0;

    // Count left node
    if (isInstance(typeOf(1), tree["leftNode"])) {
        leftSum = tree["leftNode"];
    } else {
        leftSum = sumTree(tree["leftNode"]);
    }

    // Count right node
    if (isInstance(typeOf(1), tree["rightNode"])) {
        rightSum = tree["rightNode"];
    } else {
        rightSum = sumTree(tree["rightNode"]);
    }

    return leftSum + rightSum;
}

// Create node
function createTreeNode(leftNode, rightNode) {
    node = new();
    node["leftNode"] = leftNode;
    node["rightNode"] = rightNode;
    return node;
}

// Creates a large tree, 121 nodes and verying depths going up to layers 20 deep. 
// This tree as been generated using chatgpt, as this was the easiest way to create such a large tree.
function createLargeTree() {
    return createTreeNode(
        createTreeNode(
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(1, 2),
                            createTreeNode(3,
                                createTreeNode(
                                    createTreeNode(
                                        createTreeNode(
                                            createTreeNode(
                                                createTreeNode(4, 5),
                                                createTreeNode(6, 7)
                                            ),
                                            createTreeNode(8, 9)
                                        ),
                                        createTreeNode(
                                            createTreeNode(10, 11),
                                            createTreeNode(12, 13)
                                        )
                                    ),
                                    createTreeNode(
                                        createTreeNode(
                                            createTreeNode(
                                                createTreeNode(14, 15),
                                                createTreeNode(16, 17)
                                            ),
                                            createTreeNode(18, 19)
                                        ),
                                        createTreeNode(
                                            createTreeNode(
                                                createTreeNode(
                                                    createTreeNode(20, 21),
                                                    createTreeNode(22, 23)
                                                ),
                                                createTreeNode(24, 25)
                                            ),
                                            createTreeNode(26, 27)
                                        )
                                    )
                                )
                            )
                        ),
                        createTreeNode(28, 29)
                    ),
                    createTreeNode(
                        createTreeNode(30, 31),
                        createTreeNode(
                            createTreeNode(32, 33),
                            createTreeNode(
                                createTreeNode(
                                    createTreeNode(34, 35),
                                    createTreeNode(36, 37)
                                ),
                                createTreeNode(38, 39)
                            )
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(40, 41),
                        createTreeNode(42, 43)
                    ),
                    createTreeNode(
                        createTreeNode(44, 45),
                        createTreeNode(46, 47)
                    )
                )
            ),
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(48, 49),
                        createTreeNode(
                            createTreeNode(
                                createTreeNode(50, 51),
                                createTreeNode(52, 53)
                            ),
                            createTreeNode(54, 55)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(56, 57),
                        createTreeNode(58, 59)
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(
                                createTreeNode(
                                    createTreeNode(60, 61),
                                    createTreeNode(62, 63)
                                ),
                                createTreeNode(64, 65)
                            ),
                            createTreeNode(66, 67)
                        ),
                        createTreeNode(68, 69)
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(
                                createTreeNode(
                                    createTreeNode(70, 71),
                                    createTreeNode(72, 73)
                                ),
                                createTreeNode(
                                    createTreeNode(74, 75),
                                    createTreeNode(76, 77)
                                )
                            ),
                            createTreeNode(78, 79)
                        ),
                        createTreeNode(
                            createTreeNode(80, 81),
                            createTreeNode(82, 83)
                        )
                    )
                )
            )
        ),
        createTreeNode(
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(
                                createTreeNode(
                                    createTreeNode(
                                        createTreeNode(
                                            createTreeNode(84, 85),
                                            createTreeNode(86, 87)
                                        ),
                                        createTreeNode(
                                            createTreeNode(88, 89),
                                            createTreeNode(90, 91)
                                        )
                                    ),
                                    createTreeNode(
                                        createTreeNode(
                                            createTreeNode(92, 93),
                                            createTreeNode(94, 95)
                                        ),
                                        createTreeNode(96, 97)
                                    )
                                ),
                                createTreeNode(
                                    createTreeNode(98, 99),
                                    createTreeNode(100, 101)
                                )
                            ),
                            createTreeNode(102, 103)
                        ),
                        createTreeNode(104, 105)
                    ),
                    createTreeNode(106, 107)
                ),
                createTreeNode(
                    createTreeNode(108, 109),
                    createTreeNode(110, 111)
                )
            ),
            createTreeNode(
                createTreeNode(
                    createTreeNode(112, 113),
                    createTreeNode(
                        createTreeNode(114, 115),
                        createTreeNode(
                            createTreeNode(116, 117),
                            createTreeNode(118, 119)
                        )
                    )
                ),
                createTreeNode(120, 121)
            )
        )
    );
}

// Creates a large tree balanced, 256 nodes and 8 layers deep. 
// This tree as been generated using chatgpt, as this was the easiest way to create such a large tree.
function createLargeBalancedTree() {
    return createTreeNode(
        createTreeNode(
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(1, 2),
                            createTreeNode(3, 4)
                        ),
                        createTreeNode(
                            createTreeNode(5, 6),
                            createTreeNode(7, 8)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(9, 10),
                            createTreeNode(11, 12)
                        ),
                        createTreeNode(
                            createTreeNode(13, 14),
                            createTreeNode(15, 16)
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(17, 18),
                            createTreeNode(19, 20)
                        ),
                        createTreeNode(
                            createTreeNode(21, 22),
                            createTreeNode(23, 24)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(25, 26),
                            createTreeNode(27, 28)
                        ),
                        createTreeNode(
                            createTreeNode(29, 30),
                            createTreeNode(31, 32)
                        )
                    )
                )
            ),
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(33, 34),
                            createTreeNode(35, 36)
                        ),
                        createTreeNode(
                            createTreeNode(37, 38),
                            createTreeNode(39, 40)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(41, 42),
                            createTreeNode(43, 44)
                        ),
                        createTreeNode(
                            createTreeNode(45, 46),
                            createTreeNode(47, 48)
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(49, 50),
                            createTreeNode(51, 52)
                        ),
                        createTreeNode(
                            createTreeNode(53, 54),
                            createTreeNode(55, 56)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(57, 58),
                            createTreeNode(59, 60)
                        ),
                        createTreeNode(
                            createTreeNode(61, 62),
                            createTreeNode(63, 64)
                        )
                    )
                )
            )
        ),
        createTreeNode(
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(65, 66),
                            createTreeNode(67, 68)
                        ),
                        createTreeNode(
                            createTreeNode(69, 70),
                            createTreeNode(71, 72)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(73, 74),
                            createTreeNode(75, 76)
                        ),
                        createTreeNode(
                            createTreeNode(77, 78),
                            createTreeNode(79, 80)
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(81, 82),
                            createTreeNode(83, 84)
                        ),
                        createTreeNode(
                            createTreeNode(85, 86),
                            createTreeNode(87, 88)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(89, 90),
                            createTreeNode(91, 92)
                        ),
                        createTreeNode(
                            createTreeNode(93, 94),
                            createTreeNode(95, 96)
                        )
                    )
                )
            ),
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(97, 98),
                            createTreeNode(99, 100)
                        ),
                        createTreeNode(
                            createTreeNode(101, 102),
                            createTreeNode(103, 104)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(105, 106),
                            createTreeNode(107, 108)
                        ),
                        createTreeNode(
                            createTreeNode(109, 110),
                            createTreeNode(111, 112)
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(113, 114),
                            createTreeNode(115, 116)
                        ),
                        createTreeNode(
                            createTreeNode(117, 118),
                            createTreeNode(119, 120)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(121, 122),
                            createTreeNode(123, 124)
                        ),
                        createTreeNode(
                            createTreeNode(125, 126),
                            createTreeNode(127, 128)
                        )
                    )
                )
            )
        ),
        createTreeNode(
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(129, 130),
                            createTreeNode(131, 132)
                        ),
                        createTreeNode(
                            createTreeNode(133, 134),
                            createTreeNode(135, 136)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(137, 138),
                            createTreeNode(139, 140)
                        ),
                        createTreeNode(
                            createTreeNode(141, 142),
                            createTreeNode(143, 144)
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(145, 146),
                            createTreeNode(147, 148)
                        ),
                        createTreeNode(
                            createTreeNode(149, 150),
                            createTreeNode(151, 152)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(153, 154),
                            createTreeNode(155, 156)
                        ),
                        createTreeNode(
                            createTreeNode(157, 158),
                            createTreeNode(159, 160)
                        )
                    )
                )
            ),
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(161, 162),
                            createTreeNode(163, 164)
                        ),
                        createTreeNode(
                            createTreeNode(165, 166),
                            createTreeNode(167, 168)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(169, 170),
                            createTreeNode(171, 172)
                        ),
                        createTreeNode(
                            createTreeNode(173, 174),
                            createTreeNode(175, 176)
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(177, 178),
                            createTreeNode(179, 180)
                        ),
                        createTreeNode(
                            createTreeNode(181, 182),
                            createTreeNode(183, 184)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(185, 186),
                            createTreeNode(187, 188)
                        ),
                        createTreeNode(
                            createTreeNode(189, 190),
                            createTreeNode(191, 192)
                        )
                    )
                )
            )
        ),
        createTreeNode(
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(193, 194),
                            createTreeNode(195, 196)
                        ),
                        createTreeNode(
                            createTreeNode(197, 198),
                            createTreeNode(199, 200)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(201, 202),
                            createTreeNode(203, 204)
                        ),
                        createTreeNode(
                            createTreeNode(205, 206),
                            createTreeNode(207, 208)
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(209, 210),
                            createTreeNode(211, 212)
                        ),
                        createTreeNode(
                            createTreeNode(213, 214),
                            createTreeNode(215, 216)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(217, 218),
                            createTreeNode(219, 220)
                        ),
                        createTreeNode(
                            createTreeNode(221, 222),
                            createTreeNode(223, 224)
                        )
                    )
                )
            ),
            createTreeNode(
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(225, 226),
                            createTreeNode(227, 228)
                        ),
                        createTreeNode(
                            createTreeNode(229, 230),
                            createTreeNode(231, 232)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(233, 234),
                            createTreeNode(235, 236)
                        ),
                        createTreeNode(
                            createTreeNode(237, 238),
                            createTreeNode(239, 240)
                        )
                    )
                ),
                createTreeNode(
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(241, 242),
                            createTreeNode(243, 244)
                        ),
                        createTreeNode(
                            createTreeNode(245, 246),
                            createTreeNode(247, 248)
                        )
                    ),
                    createTreeNode(
                        createTreeNode(
                            createTreeNode(249, 250),
                            createTreeNode(251, 252)
                        ),
                        createTreeNode(
                            createTreeNode(253, 254),
                            createTreeNode(255, 256)
                        )
                    )
                )
            )
        )
    );
}

function benchmark() {
  tree = createLargeTree();
  tree2 = createLargeBalancedTree();
}  


function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 5000000;
  MEASURE_FROM = 4000000;
  NAME = "Sum Tree Recursively";

  //
  // harness
  //
  time = 0;
  it = 0;

  while (it < ITERATIONS) {
    s = nanoTime();
    benchmark();
    e = nanoTime() - s;
    if (it >= MEASURE_FROM) {
      time = time + e;
    }
    it = it + 1;
  }

  avg = time / (ITERATIONS - MEASURE_FROM);
  // Make sure you print the final result -- and no other things!
  println(NAME + ": " + avg);
}
