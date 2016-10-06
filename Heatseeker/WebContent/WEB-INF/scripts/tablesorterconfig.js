$.tablesorter.addParser({
	// set a unique id 
	id : 'status',
	is : function(s) {
		// return false so this parser is not auto detected 
		return false;
	},
	format : function(s) {
		// format your data for normalization 
		return s.toLowerCase()
			.replace(/new/, 4)
			.replace(/open/, 3)
			.replace(/rejected/, 2)
			.replace(/resolved/, 1)
			.replace(/closed/, 0);
	},
	// set type, either numeric or text 
	type : 'numeric'
});

$('.tablesorter').tablesorter({
	// the 'columns' widget will require custom css for the
	// primary, secondary and tertiary columns
	// other options: "ddmmyyyy" & "yyyymmdd"
	dateFormat : "mm/dd/yyyy",

	// *** Functionality ***
	// starting sort direction "asc" or "desc"
	sortInitialOrder : "asc",
	// These are detected by default,
	// but you can change or disable them
	headers : {
		// set "sorter : false" (no quotes) to disable the column
		0 : {
			sorter : "digit"
		},
		1 : {
			sorter : "digit"
		},
		2 : {
			sorter : "text"
		},
		3 : {
			sorter : "text"
		},
		4 : {
			sorter : "text"
		},
		5 : {
			sorter : "dateFormat"
		},
		6 : {
			sorter : "status"
		}
	},
	// extract text from the table - this is how is
	// it done by default
	textExtraction : {
		0 : function(node) {
			return $(node).text();
		},
		1 : function(node) {
			return $(node).text();
		}
	},
	// forces the user to have this/these column(s) sorted first
	sortForce : null,
	// initial sort order of the columns
	// [[columnIndex, sortDirection], ... ]
	sortList : [],
	// default sort that is added to the end of the users sort
	// selection.
	sortAppend : null,
	// Use built-in javascript sort - may be faster, but does not
	// sort alphanumerically
	sortLocaleCompare : false,
	// Setting this option to true will allow you to click on the
	// table header a third time to reset the sort direction.
	sortReset : false,
	// Setting this option to true will start the sort with the
	// sortInitialOrder when clicking on a previously unsorted column.
	sortRestart : false,
	// The key used to select more than one column for multi-column
	// sorting.
	sortMultiSortKey : "shiftKey",

	// *** Customize header ***
	onRenderHeader : function() {
		// the span wrapper is added by default
		$(this).find('span').addClass('headerSpan');
	},
	// jQuery selectors used to find the header cells.
	selectorHeaders : 'thead th',

	// *** css classes to use ***
	cssAsc : "headerSortUp",
	cssChildRow : "expand-child",
	tableClass : 'tablesorter',

	// *** prevent text selection in header ***
	cancelSelection : true,

	// *** send messages to console ***
	debug : false
});