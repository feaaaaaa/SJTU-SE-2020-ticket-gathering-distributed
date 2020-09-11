describe('The Home Page', function() {
    it('successfully loads', function() {
        cy.visit('http://localhost:3000/') // change URL to match your dev URL

        cy.get('[style="padding-bottom: 17px;"] > .ant-menu-submenu-title').click()

        cy.url().should("include","login")

        cy.get('#basic_username').type("SJTU")

        cy.get('#basic_password').type("SJTU")

        cy.get('#loginBtn').click()

        cy.get('[style="padding-bottom: 17px;"] > .ant-menu-submenu-title').should("contain","SJTU")

        cy.get('#searchInput').type("周杰伦")

        cy.get('.ant-input-group-addon > .ant-btn').click()

        cy.get(':nth-child(1) > .ant-col > .ant-list-item > .wow > [style="padding-top: 20px;"] > .ant-collapse > .ant-collapse-item > .ant-collapse-header').click()

        cy.get(':nth-child(1) > [href="/detail"]').click()

        cy.get(':nth-child(9) > .ant-radio-group > .ant-radio-button-wrapper-checked').click()

        cy.get('#PurchaseButton').click()
    })
})