/**
 * Created by weichunhe on 2016/3/1.
 */
describe("账户相关", function () {
    var balance = 0; //账户余额
    beforeEach(function(done){
        $.get("/h5/account/overview",function(data){
            console.log("账户一览",data);
            expect(data.balance).toBeDefined();
            balance = data.balance;
            done();
        });
    });

    it("账户一览",function(done){
        $.get("/h5/account/overview",function(data){
            console.log("账户一览",data);
            expect(data.balance).toBeDefined();
            done();
        });
    });

    it("充值", function (done) {
        $.post("/h5/account/deposit",JSON.stringify({amount:"22"}),function(data){
            expect(data.balance-balance).toEqual(22);
            done();
        });
    });

    it("提现", function (done) {
        $.post("/h5/account/withdraw",JSON.stringify({amount:"11"}),function(data){
            expect(balance-data.balance).toEqual(11);
            done();
        });
    });
});