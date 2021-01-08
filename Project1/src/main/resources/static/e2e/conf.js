exports.config = {
    seleniumAddress:'http://localhost:4444/wd/hub',
    specs: ['protractor.spec.js']
}
//specs looks for the protractor files in the same folder.  The array can be comma seperated 
//specs: ['protractor.sepc.js', 'otherprotractor.spec.js' etc.]