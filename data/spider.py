#####################################
######      damai   #################
###### Author:feaaaaaa ##############
#####################################
# def main():
#     for i in range(1, 48):
#         response = requests.get("https://search.damai.cn/searchajax.html?keyword=&cty=&ctl=&sctl=&tsg=0&st=&et=&order=1&pageSize=30&currPage="+str(i)+"&tn=")
#         print(response.status_code)
#         print(response.text)
#         list = json.loads(response.text)
#         for item in list['pageData']['resultData']:
#             print(item)


#####################################
######    jucheng   #################
###### Author:feaaaaaa ##############
#####################################
# def main():
#
#     f = open('juchengwang.csv', 'w', encoding='utf-8')
#
#     csv_writer = csv.writer(f)
#     csv_writer.writerow(["1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15"])
#
#     for i in range(1, 17):
#         response = requests.get("http://search.juooo.com/?sort=1&ajax=yes&page="+str(i))
#         soup = BeautifulSoup(response.text, 'html.parser')
#         img = soup.find_all('img')
#         test = soup.select('div[id="tab_box2"]')[0]
#         # print(test)
#         j=2
#         for string in test.stripped_strings:
#             csv_writer.writerow([string])
#             if string[0] == '立':
#                 src = img[j].get('src')
#                 csv_writer.writerow([src])
#                 j=j+1
#
#     f.close()




if __name__ == '__main__':
    main()



