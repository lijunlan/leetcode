/**
 * Created by junlanli on 12/13/16.
 */
var host = "http://localhost:8080";
var get = true;

Vue.filter('time', function (value) {
    if (value == null || value === '') return "";
    return new Date(parseInt(value)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
});

Vue.filter('status', function (value) {
    if (value == null || value === '') return "";
    if (value === 0) {
        return "Processing";
    } else if (value === 1) {
        return "Success";
    } else if (value === 2) {
        return "Failed";
    } else {
        return "Unknown Status";
    }
});

var taskTag = new Vue({
    el: '#taskTag',
    data: {
        total: 0,
        list: []
    }
});

var page = new Vue({
    el: '#page',
    data: {
        start: 0,
        end: 9
    },
    computed: {
        canOlder: function () {
            return (this.start > 0);
        },
        canNewer: function () {
            return (this.end < taskTag.total);
        },
        pageNumber: function () {
            return this.start / 10 + 1;
        }
    },
    methods: {
        last: function () {
            if (this.start <= 0) return;
            get = false;
            var s = this.start;
            s -= 10;
            var e = this.end;
            e -= 10;
            if (s < 0) {
                e = e - s;
                s = 0;
            }
            this.start = s;
            this.end = e;
            get = true;
            updateTask();
        },
        next: function () {
            if (this.end >= taskTag.total) return;
            get = false;
            var s = this.start;
            var e = this.end;
            s += 10;
            e += 10;
            this.start = s;
            this.end = e;
            get = true;
            updateTask();
        }
    }
});

var updateTask = function () {
    if (!get) return;
    $.ajax({
        type: 'GET',
        url: host + '/console/task?start=' + page.start + '&end=' + page.end,
        success: function (data) {
            if (data.code === 200) {
                taskTag.total = data.data.total;
                taskTag.list = data.data.list;
                setTimeout(updateTask, 5000);
            } else {
                alert("list task failed\ncode: " + data.code + "\nmessage: " + data.msg);
            }
        }
    });
}

updateTask();

