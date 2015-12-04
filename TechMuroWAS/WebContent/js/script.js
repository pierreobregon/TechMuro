/*jslint white: true, browser: true, undef: true, nomen: true, eqeqeq: true, plusplus: false, bitwise: true, regexp: true, strict: true, newcap: true, immed: true, maxerr: 14 */
/*global window: false, REDIPS: true */

/* enable strict mode */
"use strict";

// create redips container
var redips = {};


// REDIPS.table initialization
redips.init = function () {
	// define reference to the REDIPS.table object
	var rt = REDIPS.table;
	// activate onmousedown event listener on cells within table with id="mainTable"
	rt.onmousedown('mainTable', true);
	// show cellIndex (it is nice for debugging)
	//rt.cell_index(true);
	// define background color for marked cell
	rt.color.cell = '#9BB3DA';
};


// function merges table cells
redips.merge = function () {
	// first merge cells horizontally and leave cells marked
	REDIPS.table.merge('h', false);
	// and then merge cells vertically and clear cells (second parameter is true by default)
	REDIPS.table.merge('v');
	
};


// function splits table cells if colspan/rowspan is greater then 1
// mode is 'h' or 'v' (cells should be marked before)
redips.split = function (mode) {
	REDIPS.table.split(mode);
};


// insert/delete table row
redips.row = function (type) {
	REDIPS.table.row('mainTable', type);
};


// insert/delete table column
redips.column = function (type) {
	REDIPS.table.column('mainTable', type);
};

redips.get_table = function (table) {
		// define output array
		var tbl = [];
		// input parameter should exits
		if (table !== undefined) {
			// if table parameter is string then set reference and overwrite input parameter
			if (typeof(table) === 'string') {
				table = document.getElementById(table);
			}
			// set table reference if table is not null and table is object and node is TABLE
			if (table && typeof(table) === 'object' && table.nodeName === 'TABLE') {
				tbl[0] = table;
			}
		}
		// return table reference as array
		return tbl;
};

redips.max_cols = function (table) {
		var	tr = table.rows,	// define number of rows in current table
			span,				// sum of colSpan values
			max = 0,			// maximum number of columns
			i, j;				// loop variable
		// if input parameter is string then overwrite it with table reference
		if (typeof(table) === 'string') {
			table = document.getElementById(table);
		}
		// open loop for each TR within table
		for (i = 0; i < tr.length; i++) {
			// reset span value
			span = 0;
			// sum colspan value for each table cell
			for (j = 0; j < tr[i].cells.length; j++) {
				span += tr[i].cells[j].colSpan || 1;
			}
			// set maximum value
		if (span > max) {
			max = span;
		}
	}
	// return maximum value
	return max;
};

redips.cell_list = function (table) {
		var matrix = [],
			matrixrow,
			lookup = {},
			c,			// current cell
			ri,			// row index
			rowspan,
			colspan,
			firstAvailCol,
			tr,			// TR collection
			i, j, k, l;	// loop variables
		// set HTML collection of table rows
		tr = table.rows;
		// open loop for each TR element
		for (i = 0; i < tr.length; i++) {
			// open loop for each cell within current row
			for (j = 0; j < tr[i].cells.length; j++) {
				// define current cell
				c = tr[i].cells[j];
				// set row index
				ri = c.parentNode.rowIndex;
				// define cell rowspan and colspan values
				rowspan = c.rowSpan || 1;
				colspan = c.colSpan || 1;
				// if matrix for row index is not defined then initialize array
				matrix[ri] = matrix[ri] || [];
				// find first available column in the first row
				for (k = 0; k < matrix[ri].length + 1; k++) {
					if (typeof(matrix[ri][k]) === 'undefined') {
						firstAvailCol = k;
						break;
					}
				}
				// set cell coordinates and reference to the table cell
				lookup[ri + '-' + firstAvailCol] = c;
				for (k = ri; k < ri + rowspan; k++) {
					matrix[k] = matrix[k] || [];
					matrixrow = matrix[k];
					for (l = firstAvailCol; l < firstAvailCol + colspan; l++) {
						matrixrow[l] = 'x';
					}
				}
			}
		}
		return lookup;
	};

// add onload event listener
if (window.addEventListener) {
	window.addEventListener('load', redips.init, false);
}
else if (window.attachEvent) {
	window.attachEvent('onload', redips.init);
}