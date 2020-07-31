describe('The Home Page', function() {
    it('successfully loads', function() {
        cy.visit('http://localhost:3000/') // change URL to match your dev URL

        cy.get('.ant-dropdown-trigger > span').click()

        cy.url().should("include","login")

        cy.visit('http://localhost:3000/') // change URL to match your dev URL

        cy.get('.ant-dropdown-trigger > span').click()

        cy.url().should("include","login")

        cy.get('#basic_username').type("CSJ")

        cy.get('#basic_password').type("CSJ")

        cy.get('#loginBtn').click()

        cy.get('.ant-dropdown-trigger').should("contain","CSJ")

        cy.get('#searchInput').type("周杰伦")

        cy.get('.ant-input-group-addon > .ant-btn').click()

        cy.url().should("include","sortPage")
        //
        cy.get(':nth-child(1) > .ant-col > .ant-list-item > [style="padding-bottom: 30px;"] > [style="padding-top: 20px;"] > .ant-collapse > .ant-collapse-item > .ant-collapse-header').click()
        //
        cy.contains("聚橙网").click()

        cy.get(':nth-child(8) > .ant-radio-group > :nth-child(2) > .ant-radio-button').click()

        cy.get('.ant-input-number-input').clear().type(4);

        cy.get('#PurchaseButton').click()

        cy.url().should("include","success")

        cy.contains("回到首页").click()

        cy.contains("CSJ").trigger('mouseover')

        cy.contains("订单管理").click()

        cy.get('#orderDiv').should("contain","周杰伦")

    })
})