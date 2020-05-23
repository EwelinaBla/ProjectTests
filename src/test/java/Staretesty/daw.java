//package PageObjects;
//
//public class daw {
//    protected override void Arrange()
//    {
//        PrepareData().Wait();
//
//        _beneficiaryData = new BeneficiaryData()
//        {
//            LastName = $"AUTO-{_randomValue}",
//            FirstName = $"TEST-{_randomValue}",
//            Gender = "Mężczyzna",
//            BirthDate = DateTime.Today.AddMonths(-6).AddDays(1),
//            StreetPlace = $"Ulica_{_randomValue}",
//            BuildingNumber = "13",
//            PostalCode = "00-123",
//            City = "Warszawa"
//        };
//
//        _productData = new ApplicationProductData()
//        {
//            RelatedEntity = "TEST",
//            ProductInContract = _pwuWorker.AddProductInContractBaseDataRequest.Name,
//            SchemaInProduct = PdfDictionaries.SchemasDictionary[SchemaType.FamilyPackage].Name,
//            MainCareRegion = "Warszawa",
//            Comments = $"Komentarz {_randomValue}"
//        };
//
//        System.LogIn(Application.PrzykładowaAplikacja);
//        System.UserContextForm.SetUserContext(DaneKontekstu)
//                .GotoMedicalCareModule()
//                .AddNewBeneficiary()
//                .FillBeneficiaryDataInApplicationForm(DaneBeneficjenta)
//                .GoToApplicationFormNextStep()
//                .FillProductDataInApplicationForm(DaneProduktu);
//    }
//
//    protected override void Act()
//    {
//        System.AddProductApplicationForm
//                .SaveApplication();
//    }
//
//    protected override void Assert()
//    {
//        System.ApplicationListForm
//                .CheckCorrectOperationSnackBar();
//    }
//}
