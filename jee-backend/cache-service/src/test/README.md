## For importing GPX files, place the following XSD files under *src/test/xsd/* and run *mvn jaxb2:testXjc*

### Basic gpx schema
http://www.topografix.com/gpx/1/0/gpx.xsd

### Groundspeak cache extension
http://www.groundspeak.com/cache/1/0/1/cache.xsd

### GSAK extension
http://www.gsak.net/xmlv1/6/gsak.xsd

To generate sources from gsak schema that will be recognized by JAXBContext, you have to alter the file as follows:
- change *<xsd:element name="wptExtension" type="wptExtension_t"/>* to *<xsd:element name="wptExtension">* and close the tag at the end after the closing *</xsd:complexType>* tag
- remove attribute *name="wptExtension_t"* from opening *complexType* tag.

## Cache source data

Place geocaching.com or GSAK GPX files under *src/test/resources/* and import them into the database using tests class *com.andreaspost.gc.cachedb.dataimport.GPXFilesImporter*.

Currently GPX version 1.0 is supported.