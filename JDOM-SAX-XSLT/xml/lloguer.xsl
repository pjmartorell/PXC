<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/llistaLloguers">
		<html>
			<head>
				<title>Llista de Lloguers</title>
			</head>
			<body>
				<br /><h1><i>Llista de Lloguers</i></h1><br />
				<table border='1' style='border-collapse:collapse; text-align:center;'>
					<tr bgcolor="#9acd32"><td><b>Model de Vehicle</b></td><td><b>Tipus de motor</b></td>
					    <td><b>Dies del lloguer</b></td><td><b>Quantitat</b></td>
					    <td><b>Descompte</b></td><td><b>Import total</b></td>
					</tr>
					<xsl:for-each select="lloguer">
					  <tr><td><xsl:value-of select="model"/></td>
					    <td><xsl:value-of select="submodel"/></td>
					    <td><xsl:value-of select="dies_lloguer"/></td>
					    <td><xsl:value-of select="num_vehicles"/></td>
					    <td><xsl:value-of select="desc"/></td>
					    <td><b><xsl:value-of select="total"/></b></td>
					   </tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
