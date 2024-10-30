function cos_init(x) {
    atan = new();
    atan[0] = 785398163397448310;
    atan[1] = 463647609000806116;
    atan[2] = 244978663126864154;
    atan[3] = 124354994546761435;
    atan[4] = 62418809995957348;
    atan[5] = 31239833430268276;
    atan[6] = 15623728620476831;
    atan[7] = 7812341060101111;
    atan[8] = 3906230131966972;
    atan[9] = 1953122516478819;
    atan[10] = 976562189559320;
    atan[11] = 488281211194898;
    atan[12] = 244140620149362;
    atan[13] = 122070311893670;
    atan[14] = 61035156174209;
    atan[15] = 30517578115526;
    atan[16] = 15258789061316;
    atan[17] = 7629394531102;
    atan[18] = 3814697265606;
    atan[19] = 1907348632810;
    atan[20] = 953674316406;
    atan[21] = 476837158203;
    atan[22] = 238418579102;
    atan[23] = 119209289551;
    atan[24] = 59604644775;
    atan[25] = 29802322388;
    atan[26] = 14901161194;
    atan[27] = 7450580597;
    atan[28] = 3725290298;
    atan[29] = 1862645149;
    atan[30] = 931322575;
    atan[31] = 465661287;
    atan[32] = 232830644;
    atan[33] = 116415322;
    atan[34] = 58207661;
    atan[35] = 29103830;
    atan[36] = 14551915;
    atan[37] = 7275958;
    atan[38] = 3637979;
    atan[39] = 1818989;
    atan[40] = 909495;
    atan[41] = 454747;
    atan[42] = 227374;
    atan[43] = 113687;
    atan[44] = 56843;
    atan[45] = 28422;
    atan[46] = 14211;
    atan[47] = 7105;
    atan[48] = 3553;
    atan[49] = 1776;
    atan[50] = 888;
    atan[51] = 444;
    atan[52] = 222;
    atan[53] = 111;
    atan[54] = 56;
    atan[55] = 28;
    atan[56] = 14;
    atan[57] = 7;
    atan[58] = 3;
    atan[59] = 2;
    return atan;
}

function cos(angle, atan, scale) {
    PI = 3141592653589793238;
    PI2 = 2 * PI;
    while (angle > PI2) {
        angle = angle - (angle / PI2 * PI2);
    }
    while (angle < 0) {
        angle = angle + ((0 - angle) / PI2 * PI2) + PI2;
    }
    quard = 0;
    while (angle > PI / 2) {
        angle = angle - PI / 2;
        quard = quard + 1;
    }

    _x = 607252935008881256;
    _y = 0;
    _z = angle;
    _i = 0;
    while (_i < 60) {
        _x0 = _x;
        _y0 = _y;
        _j = 0;
        while (_j < _i) {
            _x0 = _x0 / 2;
            _y0 = _y0 / 2;
            _j = _j + 1;
        }
        if (_z >= 0) {
            _x = _x - _y0;
            _y = _y + _x0;
            _z = _z - atan[_i];
        } else {
            _x = _x + _y0;
            _y = _y - _x0;
            _z = _z + atan[_i];
        }
        _i = _i + 1;
    }

    if (quard < 2) {
        if (quard == 0) {
            return _x;
        } else {
            return 0 - _y;
        }
    } else {
        if (quard == 2) {
            return 0 - _x;
        } else {
            return _y;
        }
    }
}

function main() {
    // simulate decimals 10^18
    scale = 1000000000000000000;
    atan = cos_init();

    i = 0;
    while (i < 64) {
        println(cos(i * scale, atan, scale));
        i = i + 1;
    }
    return;
}
