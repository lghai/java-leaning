Date.prototype.getfullMonth = function(){
    return this.getMonth()+1;
};

function lastDayOfMonth(strdate){
    return new Date(strdate.substr(0,4),strdate.substr(4,2),0);
}

function dateAdd(addCount){
    var d = new Date();
    if (d.getDate() >2) {
        return new Date(d.getFullYear(), d.getMonth(), d.getDate() + addCount);
    } else {
        return new Date(d.getFullYear(), d.getMonth(), d.getDate());
    }
}

function monthAdd(addCount) {
    var d = new Date();
    var d2 = new Date(d.getFullYear(),d.getMonth() + addCount);
    return d2.toString("yyyymm");
}

function yearAdd(addCount) {
    var d = new Date();
    var d2 = new Date(d.getFullYear() + addCount,d.getMonth());
    return d2.toString("yyyy");
}

function firstDayOfMonth() {
    var d = new Date();
    return new Date(d.getFullYear(), d.getMonth(), 1);
}

Date.prototype.add = function(addCount) {
    return new Date( this.getFullYear() ,this.getMonth(), this.getDate() + addCount);
};

Date.prototype.addDay = Date.prototype.add;

Date.prototype.addMonth = function(addCount) {
    return new Date (this.getFullYear(), this.getMonth() + addCount, this.getDate());
};

Date.prototype.addYear = function(addCount) {
    return new Date (this.getFullYear + addCount, this.getMonth(), this.getDate());
};

Date.prototype.daysAfter = function (compDate){
    return Math.round((this.getTime() - compDate.getTime()) / (100*3600*24));
};

Date.prototype.monthsAfter = function (compDate) {
    return this.yearsAfter(compDate) * 12 + this.getMonth() - compDate.getMonth();
};

Date.prototype.yearsAfter = function(compDate) {
    return this.getFullYear() - compDate.getFullYear();
};

Date.prototype.firstDayOfMonth = function() {
    return new Date(this.getFullYear(), this.getMonth(), 1);
};

Date.prototype.lastDayOfMonth = function() {
    return new Date(this.getFullYear(), this.getMonth() +1, 0);
};

Date.prototype.isAfter = function(compDate) {
    return this.getTime() > compDate.getTime();
};

Date.prototype.isAfterOrEquals = function(compDate) {
    return this.getTime() >=compDate.getTime();
};

Date.prototype.isBefore = function(compDate) {
    return this.getTime() < compDate.getTime();
};

Date.prototype.isBeforeOrEquals = function(compDate) {
    return this.getTime() <= compDate.getTime();
};

Date.prototype.isEquals = function(compDate) {
    return this.getTime() ==compDate.getTime();
};

Date.prototype.toString = function(formatter) {
    function addZero(str) {
        while (str.length < 2)
            str = '0' + str;
        return str;
    }
    var thisYear = this.getFullYear().toString();
    var thisMonth = (this.getMonth()+1).toString();
    var thisDate = this.getDate().toString();
    if (formatter == undefined){
        return thisYear + addZero(thisMonth) + addZero(thisDate);
    }else{
        return  formatter.replace('yyyy',thisYear).replace('yy',addZero((thisYear-2000).toString()))
        .replace('mm',addZero(thisMonth)).replace('m',thisMonth).replace('dd',addZero(thisDate)).replace('d',thisDate);
    }
};
