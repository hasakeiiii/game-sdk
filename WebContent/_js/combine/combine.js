/*
how to used:
<link href="../_js/combine/combine.css" rel="stylesheet" type="text/css" />
<script src="../_js/combine/combine.php" type=text/javascript></script>
<script src="../_js/combine/thickbox.js" type=text/javascript></script>
<script src="../_js/combine/combine.js" type=text/javascript></script>
<a class="img_tooltip" href="imgView.php?name=<?php echo $row["name"]?>&path=<?php echo $row["path"]?>" rel="../<?php echo $row["path"]?>" target="_blank"><?php echo $row["name"]?> [<?php echo $row["down"]?>]</a>
*/
jQuery(function(){jQuery('.tooltip').tooltip({track: true,delay: 2,showURL: false,showBody: " - "});
jQuery('.img_tooltip').tooltip({delay: 0,track: true,showURL: false,bodyHandler: function() {	return jQuery("<img/>").attr("src", this.rel);}});});