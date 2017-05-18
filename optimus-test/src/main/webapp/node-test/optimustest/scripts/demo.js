/**
 * Created by weichunhe on 2016/3/1.
 */
describe('this is a test demo', function () {

    it('sync', function () {
        var a = 1;
        expect(a).toBe(1);
    });

    it('ajax', function (done) {
        var a = 2;
        expect(a).toEqual(2);
        done();
    });

});