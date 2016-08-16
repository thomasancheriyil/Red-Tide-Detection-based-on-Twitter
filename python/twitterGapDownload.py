from datetime import datetime, timedelta
from dateutil import parser
from selenium import webdriver
import os
import unittest, time

class Sel(unittest.TestCase):
    def setUp(self):
        chromedriver = "C:\\Users\\ThomasGeorge\\Downloads\\chromedriver.exe"
        os.environ["webdriver.chrome.driver"] = chromedriver
        chromeOptions = webdriver.ChromeOptions()
        prefs = {"profile.default_content_settings" : {'images': 2}}
        chromeOptions.add_experimental_option("prefs",prefs)
        self.driver = webdriver.Chrome(executable_path=chromedriver, chrome_options=chromeOptions)
        self.driver.implicitly_wait(30)
        self.base_url = "https://twitter.com"
        self.verificationErrors = []
        self.accept_next_alert = True
    def test_sel(self):
        driver = self.driver
        delay = 2
        start = '2015-09-01'
        end = '2015-09-30'
        dt1 = parser.parse(start)
        dt2 = dt1 + timedelta(days=1)
        while dt1 < parser.parse(end):
            # url = self.base_url + "/search?q=landslide OR mudslide OR rockslide OR rockfall OR landslip since%3A" + dt1.strftime('%Y-%m-%d') + " until%3A" + dt2.strftime('%Y-%m-%d') + "&src=typd"
            url = self.base_url + "/search?q=harmful algal bloom since%3A" + dt1.strftime('%Y-%m-%d') + " until%3A" + dt2.strftime('%Y-%m-%d') + "&src=typd"
            driver.get(url)
            # driver.find_element_by_link_text("All").click()
            prev = 0
            j = 0
            for i in range(10000):
                self.driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
                time.sleep(delay)
                # check exit condition: no new items are added
                delimiter = '<div class="content">'
                try:
                    html_source = driver.page_source
                except:
                    continue
                data = html_source.encode('utf-8')
                if i % 5 == 0:
                    with open('test_%s_%s.html' % (dt1.strftime('%Y-%m-%d'), dt2.strftime('%Y-%m-%d')), 'w') as f:
                        f.write(data)
                if len(data.split(delimiter)) == prev:
                    if j == 1:
                        print 'i = %s, j = %s: no change, so we\'re exiting...' % (i, j)
                        break
                    else:
                        j = j + 1
                else:
                    prev = len(data.split(delimiter))
                    j = 0
            html_source = driver.page_source
            data = html_source.encode('utf-8')
            with open('test_%s_%s.html' % (dt1.strftime('%Y-%m-%d'), dt2.strftime('%Y-%m-%d')), 'w') as f:
                f.write(data)
            
            dt1 = dt1 + timedelta(days=1)
            dt2 = dt1 + timedelta(days=1)

if __name__ == "__main__":
    print datetime.today()
    start = time.time()

    unittest.main()

    end = time.time()
    print end - start

