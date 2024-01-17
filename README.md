## JackPine - A Geospatial Benchmark (Version 3.0)

A modified code to match current databases versions.

Changes:
```
- Manage dependencies and build code with gradle.
- Changed Dialects and tables to support new data and tools.
- Introduced PolyphenyDB support.
```

To Do:
```
- Remove or update queries for data that are not available publicly.
- Add more data support.
```

Dataset
-------
Dataset is described well in the original papers. 
Furthermore, it could be downloaded from the links below:
https://www.cs.toronto.edu/~suprio/jackpine/README
http://www.cs.toronto.edu/~suprio/jackpine/dataset/

How to run
----------
To run a single benchmark scenario called `ReadSpatialAreaContainsArea` over the Postgres:
```shell
./gradlew run --args="-include connection_postgresql_spatial.properties -props config/ReadSpatialAreaContainsArea.properties -html results/output.html"
```

To run all scenarios:
```shell
./jackpine.sh -i "connection_postgresql_spatial.properties"
```

Credits
-------

- https://github.com/shutterstock/bristlecone

If you are using this benchmark, please cite the following (original) papers.

```
@article{10.14778/3503585.3503600,
  author = {Paul, Debjyoti and Cao, Jie and Li, Feifei and Srikumar, Vivek},
  title = {Database Workload Characterization with Query Plan Encoders},
  year = {2021},
  issue_date = {December 2021},
  publisher = {VLDB Endowment},
  volume = {15},
  number = {4},
  issn = {2150-8097},
  url = {https://doi.org/10.14778/3503585.3503600},
  doi = {10.14778/3503585.3503600},
  journal = {Proc. VLDB Endow.},
  month = {dec},
  pages = {923–935},
  numpages = {13}
}
```

```
@inproceedings{ray2011jackpine,
  title={Jackpine: A benchmark to evaluate spatial database performance},
  author={Ray, Suprio and Simion, Bogdan and Brown, Angela Demke},
  booktitle={2011 IEEE 27th International Conference on Data Engineering},
  pages={1139--1150},
  year={2011},
  organization={IEEE}
}
```
