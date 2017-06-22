<?php

function endsWith($haystack, $needle) {
    return $needle === "" || (($temp = strlen($haystack) - strlen($needle)) >= 0 && strpos($haystack, $needle, $temp) !== false);
}

$dir = getcwd() . '/images';
$files = scandir($dir);

foreach ($files as $file) {
	
	if ($file == '.' || $file == '..') {
		continue;
	}

	$matched = true;
	$ext = '';
	
	if (isset($argv[1])) {
		$ext = '.' . $argv[1];
		$matched = endsWith($file, $ext);
	}
	
	if ($matched) {
		rename($dir . '/' . $file, $dir . '/' . sha1_file($dir . '/' . $file) . $ext);
	}
}
