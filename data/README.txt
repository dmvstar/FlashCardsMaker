https://community.jaspersoft.com//questions/533553/changing-backgroung-colour-base-field-value
https://community.jaspersoft.com/questions/539525/setting-styles-dynamically-here-background-color
https://community.jaspersoft.com/questions/539525/setting-styles-dynamically-here-background-color


 public void beforeReportInit() throws JRScriptletException
    {
        System.out.println("call beforeReportInit");
        JasperReport report = (JasperReport)getParameterValue(JRParameter.JASPER_REPORT);
        JRElement[] elements = report.getTitle().getElements();
        for(int i = 0; i < elements.length; i++)
        {
            JRElement element = elements[i];
//            if ("MyKey".equals(elements[i].getKey()))
//            {
                element.setBackcolor(Color.green);
                element.setForecolor(Color.blue);
                element.setMode(ModeEnum.OPAQUE);
//                break;
//            }
        }
    }

