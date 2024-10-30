/*
  * Count of steps for Frame-Stewart algorithm on 4-peg
  */

function cache_new() {
    cache = new();
    cache.len = 0;
    cache.keys = new();
    cache.map = new();
    return cache;
}

function cache_has(cache, key) {
    i = 0;
    while (i < cache.len) {
        if (cache.keys[i] == key) {
            return true;
        }
        i = i + 1;
    }
    return false;
}

function cache_get(cache, key) {
    return cache.map[key];
}

function cache_set(cache, key, value) {
    cache.keys[cache.len] = key;
    cache.len = cache.len + 1;
    cache.map[key] = value;
}

function frame_stewart(n, cache) {
    if (n == 0) {
        return 0;
    }
    if (n == 1) {
        return 1;
    }    

    if (cache_has(cache, n) == true) {
        return cache_get(cache, n);
    } else {
        k = n / 2;
        value = 2 * frame_stewart(k, cache) + frame_stewart(n - k, cache);
        cache_set(cache, n, value);
        return value;
    }
}

function main() {
    cache = cache_new();
    println(frame_stewart(4733056063952956973, cache));
}
