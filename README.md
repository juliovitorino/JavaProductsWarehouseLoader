<div id="top"></div>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->

<!-- PROJECT LOGO -->
<br />
<div align="center">
<h3 align="center">RestAPI for Product and Warehouse Loader</h3>

  <p align="center">
    This sample project example shows how to implement a load from CSV or XLSX files with REST populating a H2 in-memory database.
    <br />
    <a href="https://github.com/juliovitorino/JavaProductsWarehouseLoader"><strong>Explore the docs »</strong></a>
    <br />
    <br />
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#upload">Upload Files</a></li>
    <li><a href="#body">Body</a></li>
    <li><a href="#endpoints">Endpoints</a></li>
    <li><a href="#SKU">Valid SKU's</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



### Built With

* [Java](https://openjdk.java.net/)
* [Spring Framework](https://spring.io/)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- GETTING STARTED -->
### Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* JDK 17
* Maven 3.8.5

### Installation

Open the terminal. Put the commands below to download and start the project:
   ```sh
   git clone https://github.com/juliovitorino/JavaProductsWarehouseLoader.git
   ```
Move to the folder named by JavaProductsWarehouseLoader
   ```sh
   cd JavaProductsWarehouseLoader
   ```
Start application
   ```sh
   mvn spring-boot:run
   ```

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Files for Upload -->
## Files for upload

You can find all the files for upload and recharge the H2 database using this url https://github.com/juliovitorino/JavaProductsWarehouseLoader/tree/master/files-to-load
<p>
All files are named as indicated below.
<ul>
<li>massa-de-dados-01.csv</li>
<li>massa-de-dados-01.xlsx</li>
</ul>
</p>

<p>
If you will be use Postman Tool, use POST method followed the appropriate url and then choose form-data, fill up the Key and value informations,
where Key = file and value = name mentioned above.
</p>
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- JSON Body Examples -->
## Body Section

### Input JSON Example

``` json
{
  "sku": 50920,
  "name": "L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g",
  "warehouses": [
      {
        "locality": "MG",
        "quantity": 27,
        "type": "ECOMMERCE"
      },
      {
        "locality": "MOEMA",
        "quantity": 3,
        "type": "PHYSICAL_STORE"
      },
      {
        "locality": "Rio de Janeiro",
        "quantity": 8,
        "type": "PHYSICAL_STORE"
      }
    ]
}
```
### Output JSON Example

```json
{
  "sku": 1048,
  "name": "Amaciante Downy Brisa de Verão - 1.5L",
  "inventoryDTO": {
    "quantity": 67,
    "warehouseDTOList": [
      {
        "locality": "CE",
        "quantity": 17,
        "type": "ECOMMERCE"
      },
      {
        "locality": "RN",
        "quantity": 9,
        "type": "PHYSICAL_STORE"
      },
      {
        "locality": "RJ",
        "quantity": 20,
        "type": "PHYSICAL_STORE"
      },
      {
        "locality": "MS",
        "quantity": 2,
        "type": "ECOMMERCE"
      },
      {
        "locality": "RO",
        "quantity": 12,
        "type": "ECOMMERCE"
      },
      {
        "locality": "RS",
        "quantity": 7,
        "type": "PHYSICAL_STORE"
      }
    ]
  },
  "marketable": true
}
```

<!-- USAGE EXAMPLES -->
## Endpoints
| Method | Url                                                                                           | Body                                | Description    | Returns   | 
|--------|-----------------------------------------------------------------------------------------------|-------------------------------------|----------------|-----------|
| POST   | http://localhost:8080/upload                                                                  | form-data                           | Upload file    | -         |
| GET    | http://localhost:8080/search-product?sku=1048                                                 | {"sku": "1048"}                     | Search Product | Product   |
| POST   | http://localhost:8080/update-product                                                          | {"sku":1048,"name":"lorem ipsum"}   | Update product | Product   |
| GET    | http://localhost:8080/delete-product                                                          | null                                | Delete product | -         |
| POST   | http://localhost:8080/add-product                                                             | See Body section                    | Add Product    | Product   |

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- VALID SKUs -->
## SKU
Here follow the list with a valid sku for your purpose after you have loaded the file.
<h4><p>
9874</br>
24459</br>
26290</br>
14849</br>
31353</br>
10687</br>
41062</br>
46157</br>
12550</br>
15967</br>
11216</br>
1048</br>
22239</br>
40577</br>
1177</br>
38307</br>
40858</br>
41032</br>
32925<br>
45242</br>
41396</br>
8180</br>
10544</br>
7818</br>
4537</br>
</p></h4>

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Author

Julio Vitorino - https://github.com/juliovitorino

Project Link: [https://github.com/juliovitorino/JavaProductsWarehouseLoader](https://github.com/juliovitorino/JavaProductsWarehouseLoader)

<p align="right">(<a href="#top">back to top</a>)</p>
