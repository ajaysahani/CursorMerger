CursorMerger
====================
This library is design to insert cursor row inside another cursor or insert dummy row inside actual cursor. 

<a href="http://imgur.com/WMr0ym6"><img src="http://i.imgur.com/WMr0ym6.gif" width='275' height='428' /></a>
Features
========

* Insert cursor row at particular position inside actual cursor.
* Insert a cursor row after particular interval inside actual cursor.


Compatibility
=========
* **Library** : API 1
* **LibrarySample** : API 1


How to use library
====================
1:To create cursor row :
example:
```java
HashMap<String, Object> cursorData = new HashMap<String, Object>();
cursorData.put("_id", -1);
cursorData.put("row_identifier",5);
cursorData.put("title", "Android");
Cursor cursorRow = CursorCreator.getCursorRow(cursorData);
```

2:To insert cursor at particular position:
example:
```java
HashMap<Integer, Cursor> cursorBucket = new HashMap<Integer, Cursor>()
Cursor cursorRow1=...;
Cursor cursorRow2=...;
Cursor cursorRow2=...;
cursorBucket.put(2, cursorRow1);
cursorBucket.put(4, cursorRow2);
cursorBucket.put(5, cursorRow3);

where 2,4,5 reprsent position where cursorRow1,cursorRow2,cursorRow3 respectivily get added inside actual cursor;
```

1:To insert cursor at particular interval :
example:
```java
Cursor cursorRow=...;
Cursor mergedCursor = CursorCreator.mergeCursorAtInterval(cursorRow1,
				actualCursor, 3);
where 3 reprsents interval
```

Progaurd
========
No need to add any extra config.



License
=======
   Copyright 2014-present Ajay Sahani

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.



