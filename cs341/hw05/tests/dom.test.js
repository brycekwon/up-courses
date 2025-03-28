var fs = require('fs');

test('test selectEvent', () => {

    var html = fs.readFileSync('public/index.html', 'utf8');
    expect(html).toEqual(expect.anything());

    document.body.innerHTML = html;
    const $ = require('jquery');
    expect($('h1').html()).toBe("Cheesecake Order Form");
});
