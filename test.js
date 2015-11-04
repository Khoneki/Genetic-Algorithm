var Gene = {};

Gene = function() {
    this.gene = [];
    this.score = 0;
    this.fit = 0;
};

Gene.prototype.setGene = function() {
    for(var m = 0; m < 10; m++) {
        this.gene[m] = Math.floor(Math.random()*2)
    }
};

Gene.prototype.getScore = function() {
    this.score = 0;
    var imaginary = "0000011111";
    var arr = imaginary.split("");
    for(var i = 0; i < 10; i++) {
        if(this.gene[i] == arr[i]) this.score++;
    }
};

Gene.prototype.mutent = function () {
    var a = Math.floor(Math.random() * 100);
    if (a <= (10-this.getScore())*(10-this.getScore())) {
        for (var i = 0; i < 10; i++) {
            switch (this.gene[i]) {
                case 0:
                    this.gene[i] = 1;
                    break;
                case 1:
                    this.gene[i] = 0;
            }
        }
    }

};

Gene.prototype.clone = function() {
    var a =  JSON.parse(JSON.stringify(this, function(key, value) {
        if (typeof value === "function") { return undefined; } return value;
    }));
    var g = new Gene();
    for(var i in a){
        if(!a.hasOwnProperty(i)) continue;
        g[i] = a[i];
    }
    return a;
};


var Population = {};

Population = function() {
    this.entCnt = 100;
    this.entity = [];
    this.max = 0;
    this.min = 10;
    this.sumOfFit = 0;
    for(var i = 0; i < this.entCnt; i++)
        this.entity[i] = new Gene();
};

Population.prototype.regen = function() {
    for(var i = 0; i < this.entCnt; i++)
        this.entity[i].setGene();
};

Population.prototype.getMax = function() {
    for(var i = 0; i < this.entCnt; i ++) {
        if(this.entity[i].score > this.max) this.max = this.entity[i].score;
    }
};

Population.prototype.getMin = function() {
    for(var i = 0; i < this.entCnt; i ++) {
        if(this.entity[i].score < this.min) this.min = this.entity[i].score;
    }
};

Population.prototype.average = function() {
    var a = 0;
    for(var i = 0; i < this.entCnt; i++) {
        a += this.entity[i].score;
    }
    return a/this.entCnt;
};

Population.prototype.f = function() {
    for(var i = 0; i < this.entCnt; i++)
        this.entity[i].fit =  this.max - this.entity[i].score + (this.max - this.min) / (4 - 1);
};

Population.prototype.getSumOfFit = function() {
    this.sumOfFit = 0;
    for(var i = 0; i < this.entCnt; i++)
        this.sumOfFit += this.entity[i].fit;
};

Population.prototype.rouletteWheel = function() {
    var sum = 0;
    var a = Math.floor(Math.random()*(this.sumOfFit));
    for(var i = 0; i < this.entCnt; i++) {
        sum += this.entity[i].fit;
        if(a < sum) {
            return i;
        }
    }
};

Population.prototype.sort = function() {
    var temp;
    for(var i = 0; i < this.entCnt-2; i++) {
        for(var m = i; m < this.entCnt-1; m++) {
            if(this.entity[m].score > this.entity[m+1].score) {
                temp = this.entity[m].clone();
                this.entity[m+1] = this.entity[m].clone();
                this.entity[m+1] = temp.clone();
            }
        }
    }
};

var parent = new Population(), son = new Population();

var father, mother;
var maxOfAverage = 0, minOfAverage = 10;

function shuffle(arr) {
    if (arr instanceof Array) {
        var len = arr.length;
        if (len == 1) return arr;
        var i = len * 2;
        while (i > 0) {
            var idx1 = Math.floor(Math.random() * len);
            var idx2 = Math.floor(Math.random() * len);
            if (idx1 == idx2) continue;
            var temp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = temp;
            i--;
        }
    }
    else {
        alert("No Array Object");
    }
    return arr;
}

function cross(num) {
    var a = Math.floor(Math.random()*8+1);
    var b = Math.floor(Math.random()*(10-a)+a);
    console.log(a+" "+b);
    for(var i = 0; i < a; i++) son.entity[num].gene[i] = father.gene[i];
    for(var n = a; n < b; n++) son.entity[num].gene[n] = mother.gene[n];
    for(var m = b; m < 10; m++) son.entity[num].gene[m] = father.gene[m];
}

function init() {
    for(var m = 0; m < parent.entCnt; m++)
        parent.entity[m].getScore();
    parent.getMax();
    parent.getMin();
    parent.f();
    parent.getSumOfFit();
    var fa = parent.rouletteWheel();
    var mo = parent.rouletteWheel();
    //console.log(fa+" "+mo);
    father = parent.entity[fa].clone();
    mother = parent.entity[mo].clone();
   // console.log(father.gene.join(""));
    //console.log(mother.gene.join(""));
}

function evolution() {
    for(var o = 0; o<son.entCnt; o++) {
        cross(o);
        son.entity[o].mutent();
        son.entity[o].getScore();
    }
}

function confront() {
    //parent.sort();
    //son.sort();
    for(var i = 0; i < parent.entCnt; i++) {
        parent.entity[i] = son.entity[i].clone();
    }
    //parent.entity = shuffle(parent.entity);
}

parent.regen();
for(var m = 0; m < parent.entCnt; m++)
    parent.entity[m].getScore();
var first = parent.average();
var i;

for (i = 0; i < 2; i++) {
    console.log(i+" "+parent.average());
    if (parent.average() > maxOfAverage) maxOfAverage = parent.average();
    if (parent.average() < minOfAverage) minOfAverage = parent.average();
    if (parent.average() >= 10) break;
    init();
    for(var a = 0; a < parent.entCnt; a++) console.log(a+" "+parent.entity[a].gene);
    evolution();
    for(a = 0; a < parent.entCnt; a++) console.log(a+" "+son.entity[a].gene);
    confront();
}

console.log("first : " + first);
console.log("last : " + parent.average());

console.log("max : " + maxOfAverage);
console.log("min : " + minOfAverage);

